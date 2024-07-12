package com.example.greenstoreproject.bean.request.blog;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class BlogRequest {

    private String title;

    private String description;

    private MultipartFile thumbnail;

    private String content;

}
