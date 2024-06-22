package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.cartItem.CartItemRequest;
import com.example.greenstoreproject.bean.response.cart.CartResponse;
import com.example.greenstoreproject.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@SecurityRequirement(name = "bearerAuth")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{customerId}")
    public CartResponse getCart(@PathVariable Long customerId) {
        return cartService.getCartByCustomerId(customerId);
    }

    @PostMapping("/{customerId}/items")
    public CartResponse addItemToCart(@PathVariable Long customerId, @Valid @RequestBody CartItemRequest cartItemRequest) {
        return cartService.addItemToCart(customerId, cartItemRequest);
    }

    @PutMapping("/{customerId}/items/{cartItemId}")
    public CartResponse updateItemQuantity(@PathVariable Long customerId, @PathVariable Long cartItemId, @RequestParam int quantity) {
        return cartService.updateItemQuantity(customerId, cartItemId, quantity);
    }

    @DeleteMapping("/{customerId}/items/{cartItemId}")
    public CartResponse removeItemFromCart(@PathVariable Long customerId, @PathVariable Long cartItemId) {
        return cartService.removeItemFromCart(customerId, cartItemId);
    }

    @DeleteMapping("/{customerId}/clear")
    public void clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
    }
}
