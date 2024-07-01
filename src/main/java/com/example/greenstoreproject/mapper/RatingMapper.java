package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.Orders;
import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.entity.Rating;
import com.example.greenstoreproject.service.CustomerService;
import com.example.greenstoreproject.service.ProductService;
import com.example.greenstoreproject.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public static RatingResponse convertToResponse(Rating rating) {
        RatingResponse response = new RatingResponse();
        response.setRatingId(rating.getRatingId());
        response.setRatingValue(rating.getRatingValue());
        response.setCreateAt(rating.getCreateAt());
        response.setProductId(rating.getProduct().getProductId());
        response.setCustomerId(rating.getCustomer().getCustomerId());
        response.setOrderId(rating.getOrder().getOrderId());
        return response;
    }

    public Rating convertToEntity(RatingRequest ratingRequest, Customers customers, Products products, Orders orders) {
        Rating rating = new Rating();
        rating.setRatingValue(ratingRequest.getRatingValue());
        rating.setCreateAt(ratingRequest.getCreateAt());
        rating.setCustomer(customers);
        rating.setProduct(products);
        rating.setOrder(orders);

        return rating;
    }

}
