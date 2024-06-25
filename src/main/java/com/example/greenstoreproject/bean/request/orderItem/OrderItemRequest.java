package com.example.greenstoreproject.bean.request.orderItem;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Long productId;

    private Double quantity;

    private Double price;

}
