package com.example.greenstoreproject.bean.response.cartItem;

import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import lombok.Data;

import java.util.List;

@Data
public class CartItemResponse {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private Double price;
    private Double quantity;
    private String description;
    private Double quantityInStock;
    private List<ProductImageResponse> productImages;

}
