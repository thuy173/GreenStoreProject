package com.example.greenstoreproject.bean.response.review;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Long reviewId;
    private String content;
    private LocalDateTime createAt;
}
