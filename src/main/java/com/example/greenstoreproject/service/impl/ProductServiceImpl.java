package com.example.greenstoreproject.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.greenstoreproject.bean.request.product.ProductRequest;
import com.example.greenstoreproject.bean.request.product.ProductUpdateRequest;
import com.example.greenstoreproject.bean.response.product.ProductDetailResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.entity.Categories;
import com.example.greenstoreproject.entity.Nutrients;
import com.example.greenstoreproject.entity.ProductImages;
import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.exception.EmptyException;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.ProductMapper;
import com.example.greenstoreproject.repository.*;
import com.example.greenstoreproject.service.ProductService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final NutrientRepository nutrientRepository;
    private final ProductMapper productMapper;
    private final Cloudinary cloudinary;
    private final CartItemRepository cartItemRepository;


    @Override
    public List<ProductResponse> getAllProductStatus() {
        List<Products> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new EmptyException("Product list is Empty");
        }
        return products.stream()
                .map(productMapper::convertToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> getAllProduct(Pageable pageable) {
        Page<Products> products = productRepository.findActiveProducts(pageable);
        if(products.isEmpty()){
            throw new EmptyException("Product list is Empty");
        }
        return products.map(productMapper::convertToProductResponse);
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

        products.setCreateAt(LocalDateTime.now());

        productRepository.save(products);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    @Override
    public String updateProduct(Long id, ProductUpdateRequest productRequest) {
        Products products = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found " + id));

        Categories category = null;
        if (productRequest.getCategoryId() != null) {
            category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found " + productRequest.getCategoryId()));
        }

        List<Nutrients> nutrients = null;
        if (productRequest.getNutrientIds() != null && !productRequest.getNutrientIds().isEmpty()) {
            nutrients = nutrientRepository.findAllById(productRequest.getNutrientIds());
        }



        productMapper.updateProductFromRequest(products, productRequest, category, nutrients);
        productRepository.save(products);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }

    @Override
    public String updateProductImage(Long productId, int index, MultipartFile newImage) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found " + productId));

        if (index < 0 || index >= product.getProductImages().size()) {
            throw new IllegalArgumentException("Invalid image index");
        }

        try {
            Map uploadResult = cloudinary.uploader().upload(newImage.getBytes(), ObjectUtils.emptyMap());
            String newImageUrl = uploadResult.get("url").toString();

            ProductImages productImage = product.getProductImages().get(index);
            productImage.setImageUrl(newImageUrl);
            productImage.setProduct(product);

            productRepository.save(product);

            return SuccessMessage.SUCCESS_UPDATED.getMessage();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    public String addProductImage(Long productId, MultipartFile imageFile) {
        Optional<Products> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();

            try {
                Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());

                ProductImages productImage = new ProductImages();
                productImage.setImageUrl(uploadResult.get("url").toString());
                productImage.setProduct(product);

                product.getProductImages().add(productImage);

                productRepository.save(product);
                return SuccessMessage.SUCCESS_CREATED.getMessage();
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        } else {
            throw new NotFoundException("Product not found with id: " + productId);
        }
    }

    @Override
    @Transactional
    public void softDeleteProduct(Long productId) {
        Optional<Products> productOpt = productRepository.findById(productId);

        if (productOpt.isPresent()) {
            Products product = productOpt.get();

            product.setStatus(2);
            productRepository.save(product);

            cartItemRepository.deleteByProduct(product);
        }
    }

    @Override
    public void activateProduct(Long productId) {
        Optional<Products> products = productRepository.findById(productId);

        if(products.isPresent()){
            Products product = products.get();
            product.setStatus(1);
            productRepository.save(product);
        }
    }

    @Override
    public Page<ProductResponse> searchProducts(String name, Double minPrice, Double maxPrice, String category, Pageable pageable) {
        Page<Products> products = productRepository.searchProducts(name, minPrice,
                maxPrice, category, pageable);


        if (products.isEmpty()) {
            throw new EmptyException("No products found with the given criteria.");
        }

        return products.map(productMapper::convertToProductResponse);
    }

}
