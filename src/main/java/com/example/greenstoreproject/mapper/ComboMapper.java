package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.combo.ComboProductRequest;
import com.example.greenstoreproject.bean.request.combo.ComboRequest;
import com.example.greenstoreproject.bean.response.combo.ComboProductResponse;
import com.example.greenstoreproject.bean.response.combo.ComboResponse;
import com.example.greenstoreproject.entity.Combo;
import com.example.greenstoreproject.entity.ComboProduct;
import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ComboMapper {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Combo convertToEntity(ComboRequest comboRequest) {
        Combo combo = new Combo();
        combo.setComboName(comboRequest.getComboName());
        combo.setDescription(comboRequest.getDescription());
        combo.setBmiStatus(comboRequest.getBmiStatus());
        combo.setDuration(comboRequest.getDuration());

        List<ComboProduct> comboProducts = comboRequest.getProducts().stream()
                .map(this::toComboProduct)
                .peek(comboProduct -> comboProduct.setCombo(combo))
                .collect(Collectors.toList());

        combo.setComboProducts(comboProducts);
        combo.setPrice(calculateTotalComboPrice(comboProducts));

        return combo;
    }

    private ComboProduct toComboProduct(ComboProductRequest productRequest) {
        Products product = productRepository.findById(productRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ComboProduct comboProduct = new ComboProduct();
        comboProduct.setProduct(product);
        comboProduct.setQuantity(productRequest.getQuantity());

        return comboProduct;
    }

    private double calculateTotalComboPrice(List<ComboProduct> comboProducts) {
        return comboProducts.stream()
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum();
    }

    public ComboResponse toComboResponse(Combo combo) {
        ComboResponse response = new ComboResponse();
        response.setComboId(combo.getComboId());
        response.setComboName(combo.getComboName());
        response.setBmiStatus(combo.getBmiStatus());
        response.setDescription(combo.getDescription());
        response.setPrice(combo.getPrice());
        response.setDuration(combo.getDuration());

        List<ComboProductResponse> comboProductResponses = combo.getComboProducts().stream()
                .map(this::toComboProductResponse)
                .collect(Collectors.toList());

        response.setComboProducts(comboProductResponses);
        return response;
    }

    private ComboProductResponse toComboProductResponse(ComboProduct comboProduct) {
        ComboProductResponse response = new ComboProductResponse();
        response.setComboProductId(comboProduct.getComboProductId());
        response.setQuantity(comboProduct.getQuantity());
        response.setProducts(comboProduct.getProduct() != null ?
                List.of(productMapper.convertToProductResponse(comboProduct.getProduct())) : null);
        return response;
    }
}
