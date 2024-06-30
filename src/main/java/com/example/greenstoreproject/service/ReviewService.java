package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.review.ReviewRequest;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    String createReview(ReviewRequest review);

    List<ReviewResponse> getReviewByProductId(Long productId);
}
