package com.example.greenstoreproject.bean.response.evaluation;

import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductEvaluationResponse {
    private Double ratingValue;
    private String reviewComment;
    private String author;
    private LocalDateTime reviewTime;
    private String avatar;
}
