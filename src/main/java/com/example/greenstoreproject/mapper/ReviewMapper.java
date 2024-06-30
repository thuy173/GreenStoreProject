package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.request.review.ReviewRequest;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.Rating;
import com.example.greenstoreproject.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public static ReviewResponse convertToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(review.getReviewId());
        response.setContent(review.getContent());
        response.setCreateAt(review.getCreateAt());
        return response;
    }

    public static Review convertToEntity(ReviewRequest reviewRequest) {
        Review review = new Review();
        review.setContent(reviewRequest.getContent());
        review.setCreateAt(reviewRequest.getCreateAt());

        return review;
    }
}
