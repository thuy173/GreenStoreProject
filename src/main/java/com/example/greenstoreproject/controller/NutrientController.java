package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.nutrient.NutrientRequest;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.bean.response.nutrient.NutrientResponse;
import com.example.greenstoreproject.service.NutrientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nutrient")
@SecurityRequirement(name = "bearerAuth")
public class NutrientController {
    private final NutrientService nutrientService;

    @GetMapping
    public List<NutrientResponse> getAllNutrient() {
        return nutrientService.getAllNutrient();
    }

    @GetMapping("/{id}")
    public NutrientResponse getNutrientById(@PathVariable Long id) {
        return nutrientService.getNutrientById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public String addNutrient(@Valid @RequestBody NutrientRequest nutrientRequest) {
        return nutrientService.createNutrient(nutrientRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public String updateNutrient(@PathVariable Long id, @Valid @RequestBody NutrientRequest nutrientRequest) {
        return nutrientService.updateNutrient(id, nutrientRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteNutrient(@PathVariable Long id) {
        return nutrientService.deleteNutrient(id);
    }
}
