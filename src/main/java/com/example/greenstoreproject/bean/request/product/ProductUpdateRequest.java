package com.example.greenstoreproject.bean.request.product;

import com.example.greenstoreproject.entity.UnitOfMeasure;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductUpdateRequest {

    @Size(max = 200, message = "Product name must be between 2 and 200 characters")
    private String productName;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @Min(value = 0, message = "Quantity in stock must be non-negative")
    private Long quantityInStock;

    @Size(max = 700, message = "Description can be up to 700 characters")
    private String description;

    private LocalDate manufactureDate;

    @FutureOrPresent(message = "Expiry date must be in the present or future")
    private LocalDate expiryDate;

    private UnitOfMeasure unitOfMeasure;

    private Long categoryId;

    private List<Long> nutrientIds;
    
}
