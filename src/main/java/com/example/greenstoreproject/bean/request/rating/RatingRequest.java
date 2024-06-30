package com.example.greenstoreproject.bean.request.rating;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingRequest {

    private Integer ratingValue;

    private LocalDateTime createAt;

    private Long productId;

    private Long customerId;
}
