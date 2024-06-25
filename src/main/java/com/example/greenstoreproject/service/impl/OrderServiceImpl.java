package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.order.OrderRequest;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.entity.OrderItems;
import com.example.greenstoreproject.entity.Orders;
import com.example.greenstoreproject.mapper.OrderMapper;
import com.example.greenstoreproject.repository.CartRepository;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.repository.OrderItemRepository;
import com.example.greenstoreproject.repository.OrderRepository;
import com.example.greenstoreproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Orders orders = new Orders();
        orders.setCustomer(customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(()-> new RuntimeException("Customer not found")));



        orders.setOrderDate(orderRequest.getOrderDate());
        orders.setDiscount(orderRequest.getDiscount());
        orders.setTotalAmount(orderRequest.getTotalAmount());
        orders.setStatus(orderRequest.getStatus());
        orders.setLatitude(orderRequest.getLatitude());
        orders.setLongitude(orderRequest.getLongitude());
        orders.setShippingAddress(orderRequest.getShippingAddress());

        List<OrderItems> orderItems = cartRepository.findById(orderRequest.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"))
                .getCartItems()
                .stream()
                .map(cartItem -> {
                    OrderItems orderItem = new OrderItems();
                    orderItem.setOrder(orders);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    return orderItem;
                })
                .collect(Collectors.toList());

        orders.setOrderItems(orderItems);

        Orders savedOrder = orderRepository.save(orders);

        return orderMapper.convertToResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.convertToResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequestDTO) {
        Orders existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existingOrder.setDiscount(orderRequestDTO.getDiscount());
        existingOrder.setLatitude(orderRequestDTO.getLatitude());
        existingOrder.setLongitude(orderRequestDTO.getLongitude());
        existingOrder.setShippingAddress(orderRequestDTO.getShippingAddress());

        orderItemRepository.deleteByOrder_OrderId(existingOrder.getOrderId());

        Orders updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.convertToResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }
}
