package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.*;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.RatingMapper;
import com.example.greenstoreproject.mapper.ReviewMapper;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.repository.OrderRepository;
import com.example.greenstoreproject.repository.ProductRepository;
import com.example.greenstoreproject.repository.RatingRepository;
import com.example.greenstoreproject.service.RatingService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final RatingMapper ratingMapper;

    @Override
    public String createRating(RatingRequest ratingRequest) {
        Customers customers = customerRepository.findById(ratingRequest.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        Products products = productRepository.findById(ratingRequest.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Orders orders = orderRepository.findById(ratingRequest.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order not found"));

        String orderStatus = orderRepository.findOrderStatusByOrderId(ratingRequest.getOrderId());
        if (!"DELIVERED".equals(orderStatus)) {
            throw new IllegalStateException("Order must be delivered before rating can be created.");
        }

        Rating rating = ratingMapper.convertToEntity(ratingRequest, customers, products, orders);
        ratingRepository.save(rating);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    @Override
    public List<RatingResponse> getRatingByProductId(Long productId) {
        List<Rating> rating = ratingRepository.findByProductId(productId);
        return rating.stream()
                .map(RatingMapper::convertToResponse)
                .collect(Collectors.toList());
    }
}
