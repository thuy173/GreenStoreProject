package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.nutrient.NutrientRequest;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.bean.response.nutrient.NutrientResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NutrientService {
    List<NutrientResponse> getAllNutrient();

    NutrientResponse getNutrientById(Long id);

    String createNutrient(NutrientRequest nutrientRequest);

    String updateNutrient(Long id, NutrientRequest nutrientRequest);

    String deleteNutrient(Long id);
}
