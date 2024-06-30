package com.example.greenstoreproject.bean.response.rating;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingResponse {

    private Long ratingId;
    private Integer ratingValue;
    private LocalDateTime createAt;
}
