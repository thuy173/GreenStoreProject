package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.review.ReviewRequest;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.Review;
import com.example.greenstoreproject.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public String addReview(@RequestBody ReviewRequest review) {
        return reviewService.createReview(review);
    }

    @GetMapping("/product/{productId}")
    public List<ReviewResponse> getReviewsByProductId(@PathVariable Long productId) {
        return reviewService.getReviewByProductId(productId);
    }
}
