package com.example.greenstoreproject.bean.response.bmi;

import com.example.greenstoreproject.entity.BMIStatus;
import lombok.Data;

@Data
public class BMIResponse {
    private double bmi;
    private BMIStatus status;

    public BMIResponse(double bmi, BMIStatus status) {
        this.bmi = bmi;
        this.status = status;
    }
}
