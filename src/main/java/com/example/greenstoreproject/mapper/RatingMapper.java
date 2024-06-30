package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.entity.Rating;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {
    public static RatingResponse convertToResponse(Rating rating) {
        RatingResponse response = new RatingResponse();
        response.setRatingId(rating.getRatingId());
        response.setRatingValue(rating.getRatingValue());
        response.setCreateAt(rating.getCreateAt());
        return response;
    }

    public static Rating convertToEntity(RatingRequest ratingRequest) {
        Rating rating = new Rating();
        rating.setRatingValue(ratingRequest.getRatingValue());
        rating.setCreateAt(ratingRequest.getCreateAt());

        return rating;
    }

}
