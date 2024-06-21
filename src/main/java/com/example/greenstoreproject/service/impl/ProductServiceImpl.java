package com.example.greenstoreproject.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.greenstoreproject.bean.request.product.ProductRequest;
import com.example.greenstoreproject.bean.response.nutrient.NutrientResponse;
import com.example.greenstoreproject.bean.response.product.ProductDetailResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.entity.Categories;
import com.example.greenstoreproject.entity.Nutrients;
import com.example.greenstoreproject.entity.ProductImages;
import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.exception.EmptyException;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.ProductMapper;
import com.example.greenstoreproject.repository.CategoryRepository;
import com.example.greenstoreproject.repository.NutrientRepository;
import com.example.greenstoreproject.repository.ProductImageRepository;
import com.example.greenstoreproject.repository.ProductRepository;
import com.example.greenstoreproject.service.ProductService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final NutrientRepository nutrientRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductMapper productMapper;
    private final Cloudinary cloudinary;



    @Override
    public List<ProductResponse> getAllProduct() {
        List<Products> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new EmptyException("Product list is Empty");
        }
        return products.stream()
                .map(productMapper::convertToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailResponse getProductById(Long id) {
        Products products = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found " + id));
        return productMapper.convertToProductDetailResponse(products);
    }

    @Override
    public String createProduct(ProductRequest productRequest) {
        Categories category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found " + productRequest.getCategoryId()));

        List<Nutrients> nutrients = nutrientRepository.findAllById(productRequest.getNutrientIds());

        List<ProductImages> productImages = productRequest.getProductImages().stream()
                .map(file -> {
                    try {
                        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                        ProductImages image = new ProductImages();
                        image.setImageUrl(uploadResult.get("url").toString());
                        return image;
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload image", e);
                    }
                }).collect(Collectors.toList());

        Products products = productMapper.convertToProductEntity(productRequest, category, nutrients, productImages);
        productRepository.save(products);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }



    @Override
    public String updateProduct(Long id, ProductRequest productRequest) {
        Products products = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found " + id));
        productMapper.updateProductFromRequest(products, productRequest);
        productRepository.save(products);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }

    @Override
    public String deleteProduct(Long id) {
        Products products = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found " + id));
        productRepository.delete(products);
        return SuccessMessage.SUCCESS_DELETED.getMessage();
    }
}
