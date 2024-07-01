package com.example.greenstoreproject.bean.response.blog;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogDetailResponse {

    private Long blogId;

    private String title;

    private String content;

    private String author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean approved = false;
}
