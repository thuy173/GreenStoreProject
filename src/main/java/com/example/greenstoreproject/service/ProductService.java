package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.product.ProductRequest;
import com.example.greenstoreproject.bean.request.product.ProductUpdateRequest;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import com.example.greenstoreproject.bean.response.product.ProductDetailResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {

    List<ProductResponse> getAllProductStatus();

    Page<ProductResponse> getAllProduct(Pageable pageable);

    ProductDetailResponse getProductById(Long id);

    String createProduct(ProductRequest productRequest);

    String updateProduct(Long id, ProductUpdateRequest productRequest);

    String updateProductImage(Long productId, int index, MultipartFile newImage);

    String addProductImage(Long productId, MultipartFile imageFile);

    void softDeleteProduct(Long productId);

    void activateProduct(Long productId);

    Page<ProductResponse> searchProducts(String name, Double minPrice, Double maxPrice, String category, Pageable pageable);
}
