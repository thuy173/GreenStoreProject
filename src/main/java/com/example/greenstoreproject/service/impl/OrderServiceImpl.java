package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.order.OrderRequest;
import com.example.greenstoreproject.bean.request.orderItem.OrderItemRequest;
import com.example.greenstoreproject.bean.response.order.OrderCustomerResponse;
import com.example.greenstoreproject.bean.response.order.OrderDetailResponse;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.entity.*;
import com.example.greenstoreproject.event.NewOrderEvent;
import com.example.greenstoreproject.mapper.OrderMapper;
import com.example.greenstoreproject.repository.*;
import com.example.greenstoreproject.service.OrderService;
import com.example.greenstoreproject.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Customers customer = getOrCreateCustomer(orderRequest);
        Orders order = orderMapper.toOrder(orderRequest);
        order.setCustomer(customer);
        if (order.getTotalAmount() < 0.50) {
            throw new IllegalArgumentException("Order amount must be at least $0.50 USD");
        }
        List<OrderItems> orderItems = orderRequest.getOrderItems().stream().map(item -> {
            Products product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (product.getQuantityInStock() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getProductName());
            }
            product.setQuantityInStock(product.getQuantityInStock() - item.getQuantity());
            productRepository.save(product);
            return toOrderItem(order, item, product);
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        Orders savedOrder = orderRepository.save(order);
        updateCart(customer, orderItems);
        if ("stripe".equals(orderRequest.getPaymentMethod())) {
            try {
                PaymentIntent paymentIntent = paymentService.createPaymentIntent(
                        savedOrder.getTotalAmount().doubleValue(),
                        "usd", // đơn vị tiền tệ
                        "Order #" + savedOrder.getOrderId(),
                        customer.getEmail()
                );
                Payment payment = new Payment();
                payment.setOrder(savedOrder);
                payment.setPaymentIntentId(paymentIntent.getId());
                payment.setAmount(savedOrder.getTotalAmount());
                payment.setCurrency("usd");
                payment.setStatus("pending");

                payment = paymentRepository.save(payment);
                savedOrder.setPayment(payment);

                orderRepository.save(savedOrder);
            } catch (StripeException e) {
                log.error("Payment failed: {}", e.getMessage());
                throw new RuntimeException("Payment failed: " + e.getMessage());
            }
        } else if ("cod".equals(orderRequest.getPaymentMethod())) {
            Payment payment = new Payment();
            payment.setOrder(savedOrder);
            payment.setAmount(savedOrder.getTotalAmount());
            payment.setCurrency("usd");
            payment.setStatus("pending");

            payment = paymentRepository.save(payment);
            savedOrder.setPayment(payment);

            orderRepository.save(savedOrder);
        } else {
            throw new IllegalArgumentException("Invalid payment method");
        }

        eventPublisher.publishEvent(new NewOrderEvent(this, order));
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public OrderDetailResponse getOrderById(Long orderId) {
        Customers currentCustomer = getCurrentCustomer();
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        validateOrderAccess(currentCustomer, order);
        return orderMapper.toOrderDetailResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Orders::getOrderDate).reversed())
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderCustomerResponse> getAllOrdersByCurrentCustomer() {
        Customers currentCustomer = getCurrentCustomer();
        List<Orders> orders = orderRepository.findByCustomerCustomerId(currentCustomer.getCustomerId())
                .stream()
                .sorted(Comparator.comparing(Orders::getOrderDate).reversed())
                .collect(Collectors.toList());
        return orders.stream()
                .map(orderMapper::toOrderCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Customers currentCustomer = getCurrentCustomer();
        Orders existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        validateOrderAccess(currentCustomer, existingOrder);

        existingOrder.setShippingAddress(orderRequest.getShippingAddress());

        Orders savedOrder = orderRepository.save(existingOrder);
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void deleteOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status, boolean isAdmin) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!isAdmin && EnumSet.of(OrderStatus.PROCESSING, OrderStatus.SHIPPED, OrderStatus.DELIVERED, OrderStatus.RETURNED).contains(status)) {
            throw new RuntimeException("Permission denied: Only Admin can update to this status.");
        }

        order.setStatus(status);

        if (status == OrderStatus.CANCELED) {
            order.getOrderItems().forEach(orderItem -> {
                Products product = orderItem.getProduct();
                product.setQuantityInStock(product.getQuantityInStock() + orderItem.getQuantity());
                productRepository.save(product);
            });
        }

        Orders savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    private Customers getOrCreateCustomer(OrderRequest orderRequest) {
        if (orderRequest.getCustomerId() != null && orderRequest.getCustomerId() > 0) {
            log.info("Customer ID provided: {}", orderRequest.getCustomerId());
            return customerRepository.findById(orderRequest.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        } else {
            log.info("No customer ID provided, creating new customer.");
            Customers customer = new Customers();
            customer.setFirstName(orderRequest.getGuestName());
            customer.setEmail(orderRequest.getGuestEmail());
            customer.setPhoneNumber(orderRequest.getGuestPhone());
            return customerRepository.save(customer);
        }
    }

    private OrderItems toOrderItem(Orders order, OrderItemRequest item, Products product) {
        OrderItems orderItem = orderMapper.toOrderItem(item);
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        return orderItem;
    }

    private void updateCart(Customers customer, List<OrderItems> orderItems) {
        Carts cart = cartRepository.findByCustomerCustomerId(customer.getCustomerId());
        if (cart != null) {
            List<CartItems> cartItemsToRemove = cart.getCartItems().stream()
                    .filter(cartItem -> orderItems.stream()
                            .anyMatch(orderItem -> orderItem.getProduct().getProductId().equals(cartItem.getProduct().getProductId())))
                    .collect(Collectors.toList());
            cart.getCartItems().removeAll(cartItemsToRemove);
            cartRepository.save(cart);
        }
    }

    private void validateOrderAccess(Customers currentCustomer, Orders order) {
        if (!currentCustomer.getCustomerId().equals(order.getCustomer().getCustomerId())
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new RuntimeException("Permission denied");
        }
    }

    private Customers getCurrentCustomer() {
        String currentCustomerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerRepository.findByEmail(currentCustomerEmail);
    }

}
