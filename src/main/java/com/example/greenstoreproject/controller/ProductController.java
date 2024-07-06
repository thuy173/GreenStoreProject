package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.product.ProductRequest;
import com.example.greenstoreproject.bean.request.product.ProductUpdateRequest;
import com.example.greenstoreproject.bean.response.product.ProductDetailResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/allStatus")
    public List<ProductResponse> getAllStatusProduct() {
        return productService.getAllProductStatus();
    }

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
    public String addProduct(@Valid @ModelAttribute ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductUpdateRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}/images/{index}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProductImage(@PathVariable Long id, @PathVariable int index,
                                     @RequestParam("image") MultipartFile image) {
        return productService.updateProductImage(id, index, image);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String addProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile imageUrl) {
        return productService.addProductImage(productId, imageUrl);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/soft/{id}")
    public ResponseEntity<Void> deleteSoftProduct(@PathVariable Long id) {
        productService.softDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/active/{id}")
    public ResponseEntity<Void> activeProduct(@PathVariable Long id) {
        productService.activateProduct(id);
        return ResponseEntity.noContent().build();
    }
}
