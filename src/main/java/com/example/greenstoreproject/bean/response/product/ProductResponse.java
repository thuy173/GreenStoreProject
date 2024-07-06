package com.example.greenstoreproject.bean.response.product;

import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.entity.ProductImages;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private Long productId;

    private String productName;

    private Double price;

    private String description;

    private String unitOfMeasure;

    private Integer status;

    private List<ProductImageResponse> productImages;
}
