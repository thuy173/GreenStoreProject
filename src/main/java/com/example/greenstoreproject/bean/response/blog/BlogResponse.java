package com.example.greenstoreproject.bean.response.blog;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogResponse {

    private Long blogId;

    private String title;

    private Boolean approved = false;
}
