package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.evaluation.ProductEvaluationListRequest;
import com.example.greenstoreproject.bean.request.evaluation.ProductEvaluationRequest;
import com.example.greenstoreproject.bean.response.evaluation.ProductEvaluationResponse;
import com.example.greenstoreproject.service.ProductEvaluationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/evaluation")
@SecurityRequirement(name = "bearerAuth")
public class ProductEvaluationController {
    private final ProductEvaluationService productEvaluationService;

    @PostMapping
    public ResponseEntity<String> createProductEvaluation(@RequestBody ProductEvaluationListRequest request) {
        return ResponseEntity.ok(productEvaluationService.createProductEvaluation(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductEvaluationResponse> getEvaluationsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(productEvaluationService.getEvaluationsByProductId(productId));
    }
}
