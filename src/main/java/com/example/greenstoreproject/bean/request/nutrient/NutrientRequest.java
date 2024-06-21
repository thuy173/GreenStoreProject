package com.example.greenstoreproject.bean.request.nutrient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NutrientRequest {

    @NotBlank(message = "Nutrient name is required.")
    private String nutrientName;

    private String description;
}
