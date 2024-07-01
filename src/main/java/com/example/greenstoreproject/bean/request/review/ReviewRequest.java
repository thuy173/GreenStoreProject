package com.example.greenstoreproject.bean.request.review;

import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.Products;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewRequest {

    private String content;

    private LocalDateTime createAt;

    private Long productId;

    private Long customerId;

    private Long orderId;
}
