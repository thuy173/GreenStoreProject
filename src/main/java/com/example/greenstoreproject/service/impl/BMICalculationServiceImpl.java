package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.response.bmi.BMIResponse;
import com.example.greenstoreproject.entity.Advice;
import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.repository.AdviceRepository;
import com.example.greenstoreproject.service.BMICalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BMICalculationServiceImpl implements BMICalculationService {
    private final AdviceRepository adviceRepository;

    @Override
    public BMIResponse calculateBMI(double weight, double height) {
        double bmi = weight / (height * height);
        BMIStatus status = getStatusFromBMI(bmi);
        List<Advice> advices = getAdvicesFromStatus(status);
        Advice advice = selectRandomAdvice(advices);

        return new BMIResponse(bmi, status, advice);
    }
    private BMIStatus getStatusFromBMI(double bmi) {
        if (bmi < 18.5) {
            return BMIStatus.UNDERWEIGHT;
        } else if (bmi < 24.9) {
            return BMIStatus.NORMAL;
        } else if (bmi < 29.9) {
            return BMIStatus.OVERWEIGHT;
        } else {
            return BMIStatus.OBESE;
        }
    }

    private List<Advice> getAdvicesFromStatus(BMIStatus status) {
        return adviceRepository.findAllByStatus(status);
    }
    private Advice selectRandomAdvice(List<Advice> advices) {
        if (advices.isEmpty()) {
            throw new RuntimeException("No advice available for status ");
        }
        Random random = new Random();
        return advices.get(random.nextInt(advices.size()));
    }
}
