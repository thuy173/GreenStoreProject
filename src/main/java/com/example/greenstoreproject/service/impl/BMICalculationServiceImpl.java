package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.service.BMICalculationService;
import org.springframework.stereotype.Service;

@Service
public class BMICalculationServiceImpl implements BMICalculationService {
    @Override
    public BMIStatus calculateBMI(double weight, double height) {
        double bmi = weight / (height * height);
        if (bmi < 18.5) {
            return BMIStatus.UNDERWEIGHT;
        } else if (bmi < 25) {
            return BMIStatus.NORMAL;
        } else if (bmi < 30) {
            return BMIStatus.OVERWEIGHT;
        } else {
            return BMIStatus.OBESE;
        }
    }
}
