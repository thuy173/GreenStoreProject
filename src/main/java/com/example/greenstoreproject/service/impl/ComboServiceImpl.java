package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.combo.ComboRequest;
import com.example.greenstoreproject.bean.response.combo.ComboResponse;
import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.entity.Categories;
import com.example.greenstoreproject.entity.Combo;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.ComboMapper;
import com.example.greenstoreproject.repository.ComboRepository;
import com.example.greenstoreproject.repository.ProductRepository;
import com.example.greenstoreproject.service.ComboService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {
    private final ComboRepository comboRepository;
    private final ProductRepository productRepository;
    private final ComboMapper comboMapper;

    @Override
    public List<ComboResponse> getCombosByBMIStatus(BMIStatus status) {
        List<Combo> combos = comboRepository.findByBmiStatus(status);
        return combos.stream()
                .map(comboMapper::toComboResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ComboResponse getComboById(Integer id) {
        Combo combo = comboRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Combo not found " + id));
        return comboMapper.toComboResponse(combo);
    }

    @Override
    public Combo createCombo(ComboRequest comboRequest) {
        Combo combo = comboMapper.convertToEntity(comboRequest);
        return comboRepository.save(combo);
    }

    @Override
    public void deleteCombo(Integer comboId) {
        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new RuntimeException("Combo not found"));

        comboRepository.delete(combo);
    }
}
