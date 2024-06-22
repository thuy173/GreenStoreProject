package com.example.greenstoreproject.bean.request.product;

import com.example.greenstoreproject.entity.UnitOfMeasure;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductRequest {

    @NotNull(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String productName;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Quantity in stock is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Quantity in stock must be non-negative")
    private Double quantityInStock;

    @Size(max = 700, message = "Description can be up to 700 characters")
    private String description;

    @NotNull(message = "Manufacture date is required")
    private LocalDate manufactureDate;

    @NotNull(message = "Expiry date is required")
    @FutureOrPresent(message = "Expiry date must be in the present or future")
    private LocalDate expiryDate;

    @NotNull(message = "Unit of measure is required")
    private UnitOfMeasure unitOfMeasure;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotEmpty(message = "Nutrient IDs are required")
    private List<Long> nutrientIds;

    @NotEmpty(message = "At least one product image is required")
    private List<MultipartFile> productImages;

}
