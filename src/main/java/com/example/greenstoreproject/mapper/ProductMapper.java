package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.product.ProductRequest;
import com.example.greenstoreproject.bean.response.nutrient.NutrientResponse;
import com.example.greenstoreproject.bean.response.product.ProductDetailResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.entity.*;
import org.springframework.stereotype.Component;

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
    public ProductResponse convertToProductResponse(Products products) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(products.getProductId());
        productResponse.setProductName(products.getProductName());
        productResponse.setPrice(products.getPrice());
        productResponse.setDescription(products.getDescription());

        productResponse.setProductImages(products.getProductImages()
                .stream().map(this::convertToProductImageDTO)
                .collect(Collectors.toList()));

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
        return products;
    }

    public static void updateProductFromRequest(Products products, ProductRequest productRequest) {
        products.setProductName(productRequest.getProductName());
        products.setDescription(productRequest.getDescription());
        products.setPrice(productRequest.getPrice());
        products.setQuantityInStock(productRequest.getQuantityInStock());

    }
}
