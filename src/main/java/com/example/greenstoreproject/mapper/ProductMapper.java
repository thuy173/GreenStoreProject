package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.product.ProductRequest;
import com.example.greenstoreproject.bean.request.product.ProductUpdateRequest;
import com.example.greenstoreproject.bean.response.evaluation.ProductEvaluationResponse;
import com.example.greenstoreproject.bean.response.nutrient.NutrientResponse;
import com.example.greenstoreproject.bean.response.product.ProductDetailResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.bean.response.review.ReviewResponse;
import com.example.greenstoreproject.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final RatingMapper ratingMapper;

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

    private ReviewResponse convertToReviewResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setReviewId(review.getReviewId());
        reviewResponse.setContent(review.getContent());
        reviewResponse.setCreateAt(review.getCreateAt());
        reviewResponse.setProductId(review.getProduct().getProductId());
        reviewResponse.setCustomerId(review.getCustomer().getCustomerId());
        reviewResponse.setOrderId(review.getOrder().getOrderId());
        return reviewResponse;
    }

    public ProductResponse convertToProductResponse(Products products) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(products.getProductId());
        productResponse.setProductName(products.getProductName());
        productResponse.setPrice(products.getPrice());
        productResponse.setDescription(products.getDescription());
        productResponse.setStatus(products.getStatus());
        productResponse.setCreateAt(products.getCreateAt());
        productResponse.setQuantityInStock(products.getQuantityInStock());

        UnitOfMeasure unitOfMeasure = products.getUnitOfMeasure();
        if (unitOfMeasure != null) {
            productResponse.setUnitOfMeasure(unitOfMeasure.name());
        } else {
            productResponse.setUnitOfMeasure(null);
        }

        List<ProductImages> productImages = products.getProductImages();
        if (productImages != null && !productImages.isEmpty()) {
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

        List<ProductEvaluationResponse> ratingReviewList = new ArrayList<>();

        if (product.getRatings() != null) {
            for (Rating rating : product.getRatings()) {
                Double averageRating = product.getRatings().stream()
                        .mapToDouble(Rating::getRatingValue)
                        .average()
                        .orElse(0.0);
                productResponse.setRating(averageRating);

                ProductEvaluationResponse dto = new ProductEvaluationResponse();
                dto.setRatingValue(rating.getRatingValue());

                Review review = product.getReviews().stream()
                        .filter(r -> r.getOrder().getOrderId().equals(rating.getOrder().getOrderId())
                                && r.getProduct().getProductId().equals(rating.getProduct().getProductId()))
                        .findFirst().orElse(null);
                if (review != null) {
                    dto.setReviewComment(review.getContent());
                    dto.setAvatar(review.getCustomer().getAvatar());
                    dto.setAuthor(review.getCustomer().getFirstName() + " " + review.getCustomer().getLastName());
                    dto.setReviewTime(review.getCreateAt());
                }

                ratingReviewList.add(dto);
            }
        }

        productResponse.setRatingReviewList(ratingReviewList);

        if (product.getNutrients() != null) {
            productResponse.setNutrients(product.getNutrients()
                    .stream().map(this::convertToNutrientDTO).collect(Collectors.toList()));
        } else {
            productResponse.setNutrients(Collections.emptyList());
        }

        if (product.getProductImages() != null) {
            productResponse.setProductImages(product.getProductImages()
                    .stream().map(this::convertToProductImageDTO).collect(Collectors.toList()));
        } else {
            productResponse.setProductImages(Collections.emptyList());
        }

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
        products.setStatus(1);

        for (ProductImages image : productImages) {
            image.setProduct(products);
        }

        return products;
    }

    public static void updateProductFromRequest(Products products, ProductUpdateRequest productRequest, Categories category, List<Nutrients> nutrients) {
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
