package com.example.greenstoreproject.bean.response.bmi;

import com.example.greenstoreproject.entity.Advice;
import com.example.greenstoreproject.entity.BMIStatus;
import lombok.Data;

@Data
public class BMIResponse {
    private double bmi;
    private BMIStatus status;
    private Advice advice;

    public BMIResponse(double bmi, BMIStatus status, Advice advice) {
        this.bmi = bmi;
        this.status = status;
        this.advice = advice;
    }
}
