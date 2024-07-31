package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.response.bmi.BMIResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface BMICalculationService {
    BMIResponse calculateBMI(double weight, double height);
}
