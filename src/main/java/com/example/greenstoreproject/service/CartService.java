package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.cartItem.CartItemRequest;
import com.example.greenstoreproject.bean.response.cart.CartResponse;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    CartResponse getCartByCustomerId(Long customerId);

    CartResponse addItemToCart(Long userId, CartItemRequest cartItemRequest);

    CartResponse removeItemFromCart(Long userId, Long cartItemId);

    CartResponse updateItemQuantity(Long userId, Long cartItemId, int quantity);

    void clearCart(Long userId);
}
