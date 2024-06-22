package com.example.greenstoreproject.bean.response.cart;

import com.example.greenstoreproject.bean.response.cartItem.CartItemResponse;
import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    private Long cartId;
    private Long customerId;
    private List<CartItemResponse> cartItem;
}
