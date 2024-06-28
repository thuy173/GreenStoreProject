package com.example.greenstoreproject.bean.request.order;

import com.example.greenstoreproject.bean.request.orderItem.OrderItemRequest;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.OrderItems;
import com.example.greenstoreproject.entity.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {

    private Long customerId;

    private String guestName;

    private String guestEmail;

    private String guestPhone;

    private LocalDateTime orderDate;

    private Double discount;

    private Double totalAmount;

    private OrderStatus status;

    private Double latitude;

    private Double longitude;

    private String shippingAddress;

    private List<OrderItemRequest> orderItems;

}
