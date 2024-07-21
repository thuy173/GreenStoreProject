package com.example.greenstoreproject.bean.response.evaluation;

import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import lombok.Data;

import java.util.List;

@Data
public class ProductEvaluationResponse {
    private List<ReviewResponse> reviews;
    private List<RatingResponse> ratings;
}
