package com.example.greenstoreproject.bean.response.product;

import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.entity.ProductImages;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private Long productId;

    private String productName;

    private double price;

    private String description;

    private List<ProductImageResponse> productImages;
}
