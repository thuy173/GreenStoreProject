package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.order.OrderRequest;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.OrderItems;
import com.example.greenstoreproject.entity.Orders;
import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.mapper.OrderMapper;
import com.example.greenstoreproject.repository.*;
import com.example.greenstoreproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        Customers customer;

        if (orderRequest.getCustomerId() != null && orderRequest.getCustomerId() > 0) {
            log.info("Customer ID provided: {}", orderRequest.getCustomerId());

            customer = customerRepository.findById(orderRequest.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        } else {
            log.info("No customer ID provided, creating new customer.");

            customer = new Customers();
            customer.setFirstName(orderRequest.getGuestName());
            customer.setEmail(orderRequest.getGuestEmail());
            customer.setPhoneNumber(orderRequest.getGuestPhone());
            customer = customerRepository.save(customer);

            log.info("New customer created with ID: {}", customer.getCustomerId());

        }

        Orders order = orderMapper.toOrder(orderRequest);

        order.setCustomer(customer);
        List<OrderItems> orderItems = new ArrayList<>();
        orderRequest.getOrderItems().forEach(item -> {
            Products product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            OrderItems orderItem = orderMapper.toOrderItem(item);
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        });
        order.setOrderItems(orderItems);
        Orders savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toOrderDetailResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Orders existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Orders updatedOrder = orderMapper.toOrder(orderRequest);
        updatedOrder.setOrderId(existingOrder.getOrderId());
        updatedOrder.setCustomer(existingOrder.getCustomer());
        updatedOrder.getOrderItems().clear();
        List<OrderItems> orderItems = new ArrayList<>();
        orderRequest.getOrderItems().forEach(item -> {
            Products product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            OrderItems orderItem = orderMapper.toOrderItem(item);
            orderItem.setProduct(product);
            orderItem.setOrder(updatedOrder);
            orderItems.add(orderItem);
        });
        updatedOrder.setOrderItems(orderItems);
        Orders savedOrder = orderRepository.save(updatedOrder);
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

}
