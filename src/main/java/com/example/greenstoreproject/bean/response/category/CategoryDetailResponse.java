package com.example.greenstoreproject.bean.response.category;

import com.example.greenstoreproject.bean.response.product.ProductResponse;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDetailResponse {
    private Long categoryId;

    private String categoryName;

    private String description;

    private List<ProductResponse> products;
}
