package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.entity.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    String createRating(RatingRequest rating);

    List<RatingResponse> getRatingByProductId(Long productId);
}
