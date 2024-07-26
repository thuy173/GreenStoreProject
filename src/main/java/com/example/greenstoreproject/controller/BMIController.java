package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.bmi.BMICalculationRequest;
import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.service.BMICalculationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bmi")
@SecurityRequirement(name = "bearerAuth")
public class BMIController {
    private final BMICalculationService bmiCalculationService;

    @PostMapping("/calculate")
    public ResponseEntity<BMIStatus> calculateBMI(@RequestBody BMICalculationRequest request) {
        BMIStatus bmiStatus = bmiCalculationService.calculateBMI(request.getWeight(), request.getHeight());
        return ResponseEntity.ok(bmiStatus);
    }
}
