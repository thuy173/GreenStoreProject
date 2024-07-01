package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.review.ReviewRequest;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.*;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.CategoryMapper;
import com.example.greenstoreproject.mapper.ReviewMapper;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.repository.OrderRepository;
import com.example.greenstoreproject.repository.ProductRepository;
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
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;


    @Override
    public String createReview(ReviewRequest reviewRequest) {
        try {

            Customers customers = customerRepository.findById(reviewRequest.getCustomerId())
                    .orElseThrow(() -> new NotFoundException("Customer not found"));

            Products products = productRepository.findById(reviewRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            Orders orders = orderRepository.findById(reviewRequest.getOrderId())
                    .orElseThrow(() -> new NotFoundException("Order not found"));


            String orderStatus = orderRepository.findOrderStatusByOrderId(reviewRequest.getOrderId());
            if (!"DELIVERED".equals(orderStatus)) {
                throw new IllegalStateException("Order must be delivered before review can be created.");
            }

            Review review = reviewMapper.convertToEntity(reviewRequest, customers, products, orders);
            reviewRepository.save(review);
            return SuccessMessage.SUCCESS_CREATED.getMessage();

        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @Override
    public List<ReviewResponse> getReviewByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(ReviewMapper::convertToResponse)
                .collect(Collectors.toList());
    }
}
