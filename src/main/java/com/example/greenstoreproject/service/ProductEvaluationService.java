package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.evaluation.ProductEvaluationListRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductEvaluationService {
    String createProductEvaluation(ProductEvaluationListRequest request);
}
