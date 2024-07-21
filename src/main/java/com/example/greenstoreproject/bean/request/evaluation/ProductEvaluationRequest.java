package com.example.greenstoreproject.bean.request.evaluation;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductEvaluationRequest {

    private Double ratingValue;

    private LocalDateTime createAt;

    private Long productId;

    private Long customerId;

    private Long orderId;

    private String content;

}
