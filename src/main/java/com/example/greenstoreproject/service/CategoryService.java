package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.entity.Categories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryResponse> getAllCategory();

    CategoryDetailResponse getCategoryById(Long id);

    String createCategory(CategoryRequest categoryRequest);

    String updateCategory(Long id, CategoryRequest categoryRequest);

    String deleteCategory(Long id);
}
