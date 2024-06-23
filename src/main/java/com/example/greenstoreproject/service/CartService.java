package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.cartItem.CartItemRequest;
import com.example.greenstoreproject.bean.response.cart.CartResponse;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    CartResponse getCartByCustomerIdOrUuid(Long customerId, String cartUuid);

    CartResponse addItemToCart(Long userId, String cartUuid, CartItemRequest cartItemRequest);

    CartResponse mergeCartUponLogin(Long customerId, String cartUuid);

    CartResponse removeItemFromCart(Long userId, String cartUuid, Long cartItemId);

    CartResponse updateItemQuantity(Long userId, String cartUuid, Long cartItemId, Double quantity);

    void clearCart(Long userId, String cartUuid);
}
