package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.order.OrderRequest;
import com.example.greenstoreproject.bean.response.order.OrderCustomerResponse;
import com.example.greenstoreproject.bean.response.order.OrderDetailResponse;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.entity.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    OrderDetailResponse getOrderById(Long orderId);

    List<OrderResponse> getAllOrders();

    List<OrderCustomerResponse> getAllOrdersByCurrentCustomer();

    OrderResponse updateOrder(Long orderId, OrderRequest orderRequestDTO);

    void deleteOrder(Long orderId);

    OrderResponse updateOrderStatus(Long orderId, OrderStatus status, boolean isAdmin);

    String changeOrderStatus(Long orderId, OrderStatus status);
}
