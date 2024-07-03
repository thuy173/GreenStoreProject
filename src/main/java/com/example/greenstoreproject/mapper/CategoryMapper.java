package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.entity.Categories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final ProductMapper productMapper;

    public static CategoryResponse convertToCategoryResponse(Categories categories) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(categories.getCategoryId());
        categoryResponse.setCategoryName(categories.getCategoryName());
        return categoryResponse;
    }

    public CategoryDetailResponse convertToCategoryDetailResponse(Categories categories) {
        CategoryDetailResponse categoryResponse = new CategoryDetailResponse();
        categoryResponse.setCategoryId(categories.getCategoryId());
        categoryResponse.setCategoryName(categories.getCategoryName());
        categoryResponse.setDescription(categories.getDescription());

        categoryResponse.setProducts(categories.getProducts().stream()
                .map(productMapper::convertToProductResponse)
                .collect(Collectors.toList()));

        return categoryResponse;
    }

    public static Categories convertToCategoryEntity(CategoryRequest categoryRequest) {
        Categories categories = new Categories();
        categories.setCategoryName(categoryRequest.getCategoryName());
        categories.setDescription(categoryRequest.getDescription());

        return categories;
    }

    public static void updateCategoryFromRequest(Categories categories, CategoryRequest categoryRequest) {
        categories.setCategoryName(categoryRequest.getCategoryName());
        categories.setDescription(categoryRequest.getDescription());

    }
}
