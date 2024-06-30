package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.review.ReviewRequest;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.Categories;
import com.example.greenstoreproject.entity.Review;
import com.example.greenstoreproject.mapper.CategoryMapper;
import com.example.greenstoreproject.mapper.ReviewMapper;
import com.example.greenstoreproject.repository.ReviewRepository;
import com.example.greenstoreproject.service.ReviewService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public String createReview(ReviewRequest reviewRequest) {
        Review review = ReviewMapper.convertToEntity(reviewRequest);
        reviewRepository.save(review);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    @Override
    public List<ReviewResponse> getReviewByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(ReviewMapper::convertToResponse)
                .collect(Collectors.toList());
    }
}
