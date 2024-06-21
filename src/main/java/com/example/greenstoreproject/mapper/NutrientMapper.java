package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.nutrient.NutrientRequest;
import com.example.greenstoreproject.bean.response.nutrient.NutrientResponse;
import com.example.greenstoreproject.entity.Nutrients;
import org.springframework.stereotype.Component;

@Component
public class NutrientMapper {
    public static NutrientResponse convertToNutrientResponse(Nutrients nutrients) {
        NutrientResponse nutrientResponse = new NutrientResponse();
        nutrientResponse.setNutrientId(nutrients.getNutrientId());
        nutrientResponse.setNutrientName(nutrients.getNutrientName());
        nutrientResponse.setDescription(nutrients.getDescription());

        return nutrientResponse;
    }


    public static Nutrients convertToNutrientEntity(NutrientRequest nutrientRequest) {
        Nutrients nutrients = new Nutrients();
        nutrients.setNutrientName(nutrientRequest.getNutrientName());
        nutrients.setDescription(nutrientRequest.getDescription());

        return nutrients;
    }

    public static void updateNutrientFromRequest(Nutrients nutrients, NutrientRequest nutrientRequest) {
        nutrients.setNutrientName(nutrientRequest.getNutrientName());
        nutrients.setDescription(nutrientRequest.getDescription());

    }
}
