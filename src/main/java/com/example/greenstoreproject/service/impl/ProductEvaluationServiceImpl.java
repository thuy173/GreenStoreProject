package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.evaluation.ProductEvaluationListRequest;
import com.example.greenstoreproject.bean.request.evaluation.ProductEvaluationRequest;
import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.request.review.ReviewRequest;
import com.example.greenstoreproject.bean.response.evaluation.ProductEvaluationResponse;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.*;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.RatingMapper;
import com.example.greenstoreproject.mapper.ReviewMapper;
import com.example.greenstoreproject.repository.*;
import com.example.greenstoreproject.service.ProductEvaluationService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductEvaluationServiceImpl implements ProductEvaluationService {
    private final ReviewRepository reviewRepository;
    private final RatingRepository ratingRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;
    private final RatingMapper ratingMapper;

    @Override
    public String createProductEvaluation(ProductEvaluationListRequest requestList) {
        for (ProductEvaluationRequest request : requestList.getRequests()) {
            try {
                Customers customers = customerRepository.findById(request.getCustomerId())
                        .orElseThrow(() -> new NotFoundException("Customer not found"));

                Products products = productRepository.findById(request.getProductId())
                        .orElseThrow(() -> new NotFoundException("Product not found"));

                Orders orders = orderRepository.findById(request.getOrderId())
                        .orElseThrow(() -> new NotFoundException("Order not found"));

                if (!orders.getCustomer().getCustomerId().equals(request.getCustomerId())) {
                    throw new IllegalStateException("Order does not belong to the customer.");
                }

                boolean isProductInOrder = orderItemRepository.existsByOrderIdAndProductId(request.getOrderId(), request.getProductId());
                if (!isProductInOrder) {
                    throw new IllegalStateException("Product is not part of the order.");
                }

                String orderStatus = orderRepository.findOrderStatusByOrderId(request.getOrderId());
                if (!"DELIVERED".equals(orderStatus)) {
                    throw new IllegalStateException("Order must be delivered before evaluation can be created.");
                }

                boolean reviewExists = reviewRepository.existsByCustomer_IdAndProduct_IdAndOrder_Id(request.getCustomerId(), request.getProductId(), request.getOrderId());
                if (reviewExists) {
                    throw new IllegalStateException("Review for this product in the order already exists.");
                }

                boolean ratingExists = ratingRepository.existsByCustomer_IdAndProduct_IdAndOrder_Id(request.getCustomerId(), request.getProductId(), request.getOrderId());
                if (ratingExists) {
                    throw new IllegalStateException("Rating for this product in the order already exists.");
                }

                // Create Review
                ReviewRequest reviewRequest = new ReviewRequest();
                reviewRequest.setCustomerId(request.getCustomerId());
                reviewRequest.setProductId(request.getProductId());
                reviewRequest.setOrderId(request.getOrderId());
                reviewRequest.setContent(request.getContent());

                Review review = reviewMapper.convertToEntity(reviewRequest, customers, products, orders);
                reviewRepository.save(review);

                // Create Rating
                RatingRequest ratingRequest = new RatingRequest();
                ratingRequest.setCustomerId(request.getCustomerId());
                ratingRequest.setProductId(request.getProductId());
                ratingRequest.setOrderId(request.getOrderId());
                ratingRequest.setRatingValue(request.getRatingValue());

                Rating rating = ratingMapper.convertToEntity(ratingRequest, customers, products, orders);
                ratingRepository.save(rating);

                orders.setStatus(OrderStatus.REVIEWED);
                orderRepository.save(orders);

                addPointsToCustomer(review.getCustomer(), 1L);

            } catch (IllegalStateException e) {
                return e.getMessage();
            }
        }
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    private void addPointsToCustomer(Customers customer, Long points) {
        customer.setPoints(customer.getPoints() + points);
        customerRepository.save(customer);
    }
}
