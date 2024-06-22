package com.example.greenstoreproject.bean.request.cartItem;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    private int quantity;
}
