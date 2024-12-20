package com.example.greenstoreproject.bean.response.product;

import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.entity.ProductImages;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductResponse {

    private Long productId;

    private String productName;

    private Double price;

    private String description;

    private Double quantityInStock;

    private String unitOfMeasure;

    private Integer status;

    private List<ProductImageResponse> productImages;

    private LocalDateTime createAt;
}
