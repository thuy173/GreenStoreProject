package com.example.greenstoreproject.bean.request.order;

import com.example.greenstoreproject.bean.request.orderItem.OrderItemRequest;
import com.example.greenstoreproject.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequest {

    private Long customerId;

    private Long cartId;

    private LocalDate orderDate;

    private Double discount;

    private Double totalAmount;

    private OrderStatus status;

    private Double latitude;

    private Double longitude;

    private String shippingAddress;

}
