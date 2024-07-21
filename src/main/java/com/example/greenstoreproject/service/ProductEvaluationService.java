package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.evaluation.ProductEvaluationListRequest;
import com.example.greenstoreproject.bean.response.evaluation.ProductEvaluationResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductEvaluationService {
    String createProductEvaluation(ProductEvaluationListRequest request);
    ProductEvaluationResponse getEvaluationsByProductId(Long productId);
}
