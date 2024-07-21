package com.example.greenstoreproject.bean.request.evaluation;

import lombok.Data;

import java.util.List;

@Data
public class ProductEvaluationListRequest {
    private List<ProductEvaluationRequest> requests;
}
