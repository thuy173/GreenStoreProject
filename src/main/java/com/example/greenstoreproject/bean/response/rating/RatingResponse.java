package com.example.greenstoreproject.bean.response.rating;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingResponse {

    private Long ratingId;
    private Double ratingValue;
    private LocalDateTime createAt;
    private Long productId;
    private Long customerId;
    private Long orderId;
}
