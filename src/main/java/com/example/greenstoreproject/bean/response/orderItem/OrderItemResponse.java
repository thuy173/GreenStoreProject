package com.example.greenstoreproject.bean.response.orderItem;

import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemResponse {

    private Long orderItemId;

    private Long productId;

    private String productName;

    private String description;

    private Double quantity;

    private Double price;

    private Double totalPrice;

    private List<ProductImageResponse> productImages;
}
