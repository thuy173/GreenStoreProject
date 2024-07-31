package com.example.greenstoreproject.bean.request.order;

import com.example.greenstoreproject.bean.request.orderItem.OrderItemRequest;
import com.example.greenstoreproject.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {

    private Long customerId;

    private String guestName;

    private String guestEmail;

    private String guestPhone;

    private LocalDateTime orderDate;

    private Long voucherId;

    private Double totalAmount;

    private OrderStatus status;

    private Double latitude;

    private Double longitude;

    private String shippingAddress;

    private String paymentMethod;

    private List<OrderItemRequest> orderItems;

    private boolean isComboOrder;

    private Integer comboId;
}
