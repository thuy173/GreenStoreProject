package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.request.review.ReviewRequest;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.*;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public static ReviewResponse convertToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(review.getReviewId());
        response.setContent(review.getContent());
        response.setCreateAt(review.getCreateAt());
        response.setCustomerId(review.getCustomer().getCustomerId());
        response.setProductId(review.getProduct().getProductId());
        response.setOrderId(review.getOrder().getOrderId());
        return response;
    }

    public Review convertToEntity(ReviewRequest reviewRequest, Customers customers, Products products, Orders orders) {
        Review review = new Review();
        review.setContent(reviewRequest.getContent());
        review.setCreateAt(reviewRequest.getCreateAt());
        review.setCustomer(customers);
        review.setProduct(products);
        review.setOrder(orders);

        return review;
    }
}
