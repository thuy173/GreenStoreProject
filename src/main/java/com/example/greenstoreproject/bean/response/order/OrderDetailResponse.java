package com.example.greenstoreproject.bean.response.order;

import com.example.greenstoreproject.bean.response.orderItem.OrderItemResponse;
import com.example.greenstoreproject.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailResponse {
    private Long orderId;

    private Long customerId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDateTime orderDate;

    private String orderCode;

    private Long voucherId;

    private Double totalAmount;

    private OrderStatus status;

    private Double latitude;

    private Double longitude;

    private String shippingAddress;

    private String paymentMethod;

    private List<OrderItemResponse> orderItems;
}
