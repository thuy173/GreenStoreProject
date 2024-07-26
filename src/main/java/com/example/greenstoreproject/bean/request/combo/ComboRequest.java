package com.example.greenstoreproject.bean.request.combo;

import com.example.greenstoreproject.entity.BMIStatus;
import lombok.Data;

import java.util.List;

@Data
public class ComboRequest {
    private String comboName;
    private String description;
    private BMIStatus bmiStatus;
    private List<ComboProductRequest> products;
}
