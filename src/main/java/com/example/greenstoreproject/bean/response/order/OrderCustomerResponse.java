package com.example.greenstoreproject.bean.response.order;

import com.example.greenstoreproject.bean.response.orderItem.OrderItemResponse;
import com.example.greenstoreproject.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderCustomerResponse {
    private Long orderId;

    private LocalDateTime orderDate;

    private String orderCode;

    private Long voucherId;

    private Double totalAmount;

    private OrderStatus status;

    private String paymentMethod;

    private List<OrderItemResponse> orderItems;
}
