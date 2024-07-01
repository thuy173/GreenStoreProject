package com.example.greenstoreproject.bean.request.blog;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogRequest {

    private String title;

    private String content;

    private String author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean approved = false;
}
