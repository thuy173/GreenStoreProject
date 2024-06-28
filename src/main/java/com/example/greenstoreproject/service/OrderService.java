package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.order.OrderRequest;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.entity.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getAllOrders();

    OrderResponse updateOrder(Long orderId, OrderRequest orderRequestDTO);

    void deleteOrder(Long orderId);

    OrderResponse updateOrderStatus(Long orderId, OrderStatus status, boolean isAdmin);
}
