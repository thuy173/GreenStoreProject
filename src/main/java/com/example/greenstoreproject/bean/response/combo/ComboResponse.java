package com.example.greenstoreproject.bean.response.combo;

import com.example.greenstoreproject.entity.BMIStatus;
import lombok.Data;

import java.util.List;

@Data
public class ComboResponse {
    private int comboId;
    private String comboName;
    private BMIStatus bmiStatus;
    private String description;
    private Double price;
    private int duration;
    private List<ComboProductResponse> comboProducts;
}
