package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.entity.Categories;
import com.example.greenstoreproject.exception.EmptyException;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.CategoryMapper;
import com.example.greenstoreproject.repository.CategoryRepository;
import com.example.greenstoreproject.service.CategoryService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Categories> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new EmptyException("Category list is Empty");
        }
        return categories.stream()
                .map(CategoryMapper::convertToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDetailResponse getCategoryById(Long id) {
        Categories category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found " + id));
        return CategoryMapper.convertToCategoryDetailResponse(category);
    }

    @Override
    public String createCategory(CategoryRequest categoryRequest) {
        Categories categories = CategoryMapper.convertToCategoryEntity(categoryRequest);
        categoryRepository.save(categories);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    @Override
    public String updateCategory(Long id, CategoryRequest categoryRequest) {
        Categories categories = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found " + id));
        CategoryMapper.updateCategoryFromRequest(categories, categoryRequest);
        categoryRepository.save(categories);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }

    @Override
    public String deleteCategory(Long id) {
        Categories categories = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found " + id));
        categoryRepository.delete(categories);
        return SuccessMessage.SUCCESS_DELETED.getMessage();
    }
}
