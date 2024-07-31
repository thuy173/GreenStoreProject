package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.response.bmi.BMIResponse;
import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.service.BMICalculationService;
import org.springframework.stereotype.Service;

@Service
public class BMICalculationServiceImpl implements BMICalculationService {

    @Override
    public BMIResponse calculateBMI(double weight, double height) {
        double bmi = weight / (height * height);
        BMIStatus status;
        if (bmi < 18.5) {
            status = BMIStatus.UNDERWEIGHT;
        } else if (bmi < 25) {
            status = BMIStatus.NORMAL;
        } else if (bmi < 30) {
            status = BMIStatus.OVERWEIGHT;
        } else {
            status = BMIStatus.OBESE;
        }

        return new BMIResponse(bmi, status);
    }
}
