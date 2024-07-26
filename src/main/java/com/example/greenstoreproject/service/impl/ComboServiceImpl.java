package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.combo.ComboProductRequest;
import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.entity.Combo;
import com.example.greenstoreproject.entity.ComboProduct;
import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.repository.ComboProductRepository;
import com.example.greenstoreproject.repository.ComboRepository;
import com.example.greenstoreproject.repository.ProductRepository;
import com.example.greenstoreproject.service.ComboService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {
    private final ComboRepository comboRepository;
    private final ComboProductRepository comboProductRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Combo> getCombosByBMIStatus(BMIStatus status) {
        return comboRepository.findByBmiStatus(status);
    }

    @Override
    public Combo createCombo(String comboName, String description, BMIStatus status, List<ComboProductRequest> products) {
        Combo combo = new Combo();
        combo.setComboName(comboName);
        combo.setDescription(description);
        combo.setBmiStatus(status);

        double totalComboPrice = 0;
        for (ComboProductRequest productRequest : products) {
            Products product = productRepository.findById(productRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ComboProduct comboProduct = new ComboProduct();
            comboProduct.setProduct(product);
            comboProduct.setQuantity(productRequest.getQuantity());
            comboProduct.setCombo(combo);

            combo.getComboProducts().add(comboProduct);

            totalComboPrice += product.getPrice() * productRequest.getQuantity();
        }
        combo.setPrice(totalComboPrice);

        return comboRepository.save(combo);
    }

    @Override
    public void deleteCombo(Integer comboId) {
        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new RuntimeException("Combo not found"));

        comboRepository.delete(combo);
    }
}
