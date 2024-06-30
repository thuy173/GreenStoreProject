package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.Rating;
import com.example.greenstoreproject.entity.Review;
import com.example.greenstoreproject.mapper.RatingMapper;
import com.example.greenstoreproject.mapper.ReviewMapper;
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

    @Override
    public String createRating(RatingRequest ratingRequest) {
        Rating rating = RatingMapper.convertToEntity(ratingRequest);
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
