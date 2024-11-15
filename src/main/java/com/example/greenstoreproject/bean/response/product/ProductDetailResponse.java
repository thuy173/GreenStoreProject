package com.example.greenstoreproject.bean.response.product;

import com.example.greenstoreproject.bean.response.evaluation.ProductEvaluationResponse;
import com.example.greenstoreproject.bean.response.nutrient.NutrientResponse;
import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductDetailResponse {

    private Long productId;

    private String productName;

    private Double price;

    private Double quantityInStock;

    private String description;

    private LocalDate manufactureDate;

    private LocalDate expiryDate;

    private String unitOfMeasure;

    private String categoryName;

    private List<NutrientResponse> nutrients;

    private List<ProductImageResponse> productImages;

    private Double rating;

    private List<ProductEvaluationResponse> ratingReviewList;

}
