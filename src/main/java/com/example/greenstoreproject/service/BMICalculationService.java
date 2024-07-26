package com.example.greenstoreproject.service;

import com.example.greenstoreproject.entity.BMIStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface BMICalculationService {
    BMIStatus calculateBMI(double weight, double height);
}
