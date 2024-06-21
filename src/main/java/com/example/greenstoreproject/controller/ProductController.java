package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.product.ProductRequest;
import com.example.greenstoreproject.bean.response.product.ProductDetailResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public ProductDetailResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addProduct(@Valid @ModelAttribute  ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }


}
