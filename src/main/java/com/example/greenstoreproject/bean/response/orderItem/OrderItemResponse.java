package com.example.greenstoreproject.bean.response.orderItem;

import lombok.Data;

@Data
public class OrderItemResponse {

    private Long orderItemId;

    private Long productId;

    private Double quantity;

    private Double price;
}
