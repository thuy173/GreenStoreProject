package com.example.greenstoreproject.bean.response.blog;

import lombok.Data;

@Data
public class BlogResponse {

    private Long blogId;

    private String title;

    private String description;

    private String thumbnail;

    private String createAt;

}
