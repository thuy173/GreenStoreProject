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

    @GetMapping("/{customerIdOrUuid}")
    public CartResponse getCart(@PathVariable String customerIdOrUuid) {
        Long customerId = null;
        String cartUuid = null;
        try {
            customerId = Long.parseLong(customerIdOrUuid);
        } catch (NumberFormatException e) {
            cartUuid = customerIdOrUuid;
        }
        return cartService.getCartByCustomerIdOrUuid(customerId, cartUuid);
    }


    @PostMapping("/{customerIdOrUuid}/items")
    public CartResponse addItemToCart(@PathVariable String customerIdOrUuid, @Valid @RequestBody CartItemRequest cartItemRequest) {
        Long customerId = null;
        String cartUuid = null;
        try {
            customerId = Long.parseLong(customerIdOrUuid);
        } catch (NumberFormatException e) {
            cartUuid = customerIdOrUuid;
        }
        return cartService.addItemToCart(customerId, cartUuid, cartItemRequest);
    }

    @PutMapping("/{customerIdOrUuid}/items/{cartItemId}")
    public CartResponse updateItemQuantity(@PathVariable String customerIdOrUuid, @PathVariable Long cartItemId, @RequestParam Double quantity) {
        Long customerId = null;
        String cartUuid = null;
        try {
            customerId = Long.parseLong(customerIdOrUuid);
        } catch (NumberFormatException e) {
            cartUuid = customerIdOrUuid;
        }
        return cartService.updateItemQuantity(customerId, cartUuid, cartItemId, quantity);
    }

    @DeleteMapping("/{customerIdOrUuid}/items/{cartItemId}")
    public CartResponse removeItemFromCart(@PathVariable String customerIdOrUuid, @PathVariable Long cartItemId) {
        Long customerId = null;
        String cartUuid = null;
        try {
            customerId = Long.parseLong(customerIdOrUuid);
        } catch (NumberFormatException e) {
            cartUuid = customerIdOrUuid;
        }
        return cartService.removeItemFromCart(customerId, cartUuid, cartItemId);
    }

    @DeleteMapping("/{customerIdOrUuid}/clear")
    public void clearCart(@PathVariable String customerIdOrUuid) {
        Long customerId = null;
        String cartUuid = null;
        try {
            customerId = Long.parseLong(customerIdOrUuid);
        } catch (NumberFormatException e) {
            cartUuid = customerIdOrUuid;
        }
        cartService.clearCart(customerId, cartUuid);
    }
}
