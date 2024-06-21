package com.example.greenstoreproject.bean.request.product;

import com.example.greenstoreproject.entity.UnitOfMeasure;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductRequest {

    private String productName;

    private double price;

    private Long quantityInStock;

    private String description;

    private LocalDate manufactureDate;

    private LocalDate expiryDate;

    private UnitOfMeasure unitOfMeasure;

    private Long categoryId;

    private List<Long> nutrientIds;

    private List<MultipartFile> productImages;

}
