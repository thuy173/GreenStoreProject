package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.response.cart.CartResponse;
import com.example.greenstoreproject.bean.response.cartItem.CartItemResponse;
import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.entity.CartItems;
import com.example.greenstoreproject.entity.Carts;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.ProductImages;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {
    public CartResponse mapToCartResponse(Carts cart) {
        if (cart == null) {
            return null;
        }
        CartResponse cartResponse = new CartResponse();
        Customers customer = cart.getCustomer();
        if (customer != null) {
            cartResponse.setCustomerId(customer.getCustomerId());
        } else {
            cartResponse.setCustomerId(null);
        }
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setCartItem(cart.getCartItems().stream()
                .map(this::mapToCartItemResponse).collect(Collectors.toList()));

        return cartResponse;
    }

    private CartItemResponse mapToCartItemResponse(CartItems cartItem) {
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setCartItemId(cartItem.getCartItemId());
        cartItemResponse.setProductId(cartItem.getProduct().getProductId());
        cartItemResponse.setProductName(cartItem.getProduct().getProductName());
        cartItemResponse.setPrice(cartItem.getProduct().getPrice());
        cartItemResponse.setQuantity(cartItem.getQuantity());
        cartItemResponse.setQuantityInStock(cartItem.getProduct().getQuantityInStock());
        cartItemResponse.setDescription(cartItem.getProduct().getDescription());
        cartItemResponse.setProductImages(mapToProductImageResponses(cartItem.getProduct().getProductImages()));

        if (cartItem.getQuantity() != null && cartItem.getQuantity() > 0) {
            cartItemResponse.setTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());
        } else {
            cartItemResponse.setTotalPrice(0.0);
        }
        return cartItemResponse;
    }
    private List<ProductImageResponse> mapToProductImageResponses(List<ProductImages> productImages) {
        List<ProductImageResponse> productImageResponses = new ArrayList<>();
        if (productImages != null && !productImages.isEmpty()) {
            ProductImages firstProductImage = productImages.get(0);
            ProductImageResponse productImageResponse = new ProductImageResponse();
            productImageResponse.setProductImageId(firstProductImage.getProductImageId());
            productImageResponse.setImageUrl(firstProductImage.getImageUrl());
            productImageResponses.add(productImageResponse);
        }
        return productImageResponses;
    }
}
