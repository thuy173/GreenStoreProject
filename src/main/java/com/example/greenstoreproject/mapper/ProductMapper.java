package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.product.ProductRequest;
import com.example.greenstoreproject.bean.request.product.ProductUpdateRequest;
import com.example.greenstoreproject.bean.response.nutrient.NutrientResponse;
import com.example.greenstoreproject.bean.response.product.ProductDetailResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private NutrientResponse convertToNutrientDTO(Nutrients nutrient) {
        NutrientResponse dto = new NutrientResponse();
        dto.setNutrientId(nutrient.getNutrientId());
        dto.setNutrientName(nutrient.getNutrientName());
        return dto;
    }

    private ProductImageResponse convertToProductImageDTO(ProductImages image) {
        ProductImageResponse dto = new ProductImageResponse();
        dto.setProductImageId(image.getProductImageId());
        dto.setImageUrl(image.getImageUrl());
        return dto;
    }

    private RatingResponse convertToRatingResponse(Rating rating) {
        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setRatingId(rating.getRatingId());
        ratingResponse.setRatingValue(rating.getRatingValue());
        ratingResponse.setCreateAt(rating.getCreateAt());
        return ratingResponse;
    }

    private ReviewResponse convertToReviewResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setReviewId(review.getReviewId());
        reviewResponse.setContent(review.getContent());
        reviewResponse.setCreateAt(review.getCreateAt());
        return reviewResponse;
    }

    public ProductResponse convertToProductResponse(Products products) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(products.getProductId());
        productResponse.setProductName(products.getProductName());
        productResponse.setPrice(products.getPrice());
        productResponse.setDescription(products.getDescription());

        UnitOfMeasure unitOfMeasure = products.getUnitOfMeasure();
        if (unitOfMeasure != null) {
            productResponse.setUnitOfMeasure(unitOfMeasure.name());
        } else {
            productResponse.setUnitOfMeasure(null);
        }

        List<ProductImages> productImages = products.getProductImages();
        if (!productImages.isEmpty()) {
            ProductImageResponse imageResponse = convertToProductImageDTO(productImages.get(0));
            productResponse.setProductImages(Collections.singletonList(imageResponse));
        } else {
            productResponse.setProductImages(Collections.emptyList());
        }

        return productResponse;
    }

    public ProductDetailResponse convertToProductDetailResponse(Products product) {
        ProductDetailResponse productResponse = new ProductDetailResponse();
        productResponse.setProductId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantityInStock(product.getQuantityInStock());
        productResponse.setDescription(product.getDescription());
        productResponse.setManufactureDate(product.getManufactureDate());
        productResponse.setExpiryDate(product.getExpiryDate());

        Double averageRating = product.getRatings().stream()
                .mapToDouble(Rating::getRatingValue)
                .average()
                .orElse(0.0);
        productResponse.setRating(averageRating);

        List<RatingResponse> ratingResponses = product.getRatings().stream()
                .map(this::convertToRatingResponse)
                .collect(Collectors.toList());
        productResponse.setRatingList(ratingResponses);

        List<ReviewResponse> reviewResponses = product.getReviews().stream()
                .map(this::convertToReviewResponse)
                .collect(Collectors.toList());
        productResponse.setReview(reviewResponses);

        productResponse.setNutrients(product.getNutrients()
                .stream().map(this::convertToNutrientDTO).collect(Collectors.toList()));

        productResponse.setProductImages(product.getProductImages()
                .stream().map(this::convertToProductImageDTO).collect(Collectors.toList()));

        UnitOfMeasure unitOfMeasure = product.getUnitOfMeasure();
        if (unitOfMeasure != null) {
            productResponse.setUnitOfMeasure(unitOfMeasure.name());
        } else {
            productResponse.setUnitOfMeasure(null);
        }

        Categories category = product.getCategory();
        if (category != null) {
            productResponse.setCategoryName(category.getCategoryName());
        } else {
            productResponse.setCategoryName(null);
        }

        return productResponse;
    }

    public static Products convertToProductEntity(ProductRequest productRequest, Categories category, List<Nutrients> nutrients, List<ProductImages> productImages) {
        Products products = new Products();
        products.setProductName(productRequest.getProductName());
        products.setPrice(productRequest.getPrice());
        products.setQuantityInStock(productRequest.getQuantityInStock());
        products.setDescription(productRequest.getDescription());
        products.setManufactureDate(productRequest.getManufactureDate());
        products.setExpiryDate(productRequest.getExpiryDate());
        products.setUnitOfMeasure(productRequest.getUnitOfMeasure());
        products.setCategory(category);
        products.setNutrients(nutrients);
        products.setProductImages(productImages);

        for (ProductImages image : productImages) {
            image.setProduct(products);
        }

        return products;
    }

    public static void updateProductFromRequest(Products products, ProductUpdateRequest productRequest, Categories category,
                                                List<Nutrients> nutrients
    ) {
        if (productRequest.getProductName() != null && !productRequest.getProductName().isEmpty()) {
            products.setProductName(productRequest.getProductName());
        }
        if (productRequest.getDescription() != null && !productRequest.getDescription().isEmpty()) {
            products.setDescription(productRequest.getDescription());
        }
        if (productRequest.getPrice() != null) {
            products.setPrice(productRequest.getPrice());
        }
        if (productRequest.getQuantityInStock() != null) {
            products.setQuantityInStock(productRequest.getQuantityInStock());
        }
        if (productRequest.getManufactureDate() != null) {
            products.setManufactureDate(productRequest.getManufactureDate());
        }
        if (productRequest.getExpiryDate() != null) {
            products.setExpiryDate(productRequest.getExpiryDate());
        }
        if (productRequest.getUnitOfMeasure() != null) {
            products.setUnitOfMeasure(productRequest.getUnitOfMeasure());
        }
        if (category != null) {
            products.setCategory(category);
        }
        if (nutrients != null && !nutrients.isEmpty()) {
            products.setNutrients(nutrients);
        }


    }
}
