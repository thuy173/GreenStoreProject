package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.combo.ComboRequest;
import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.entity.Combo;
import com.example.greenstoreproject.mapper.ComboMapper;
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
    private final ProductRepository productRepository;
    private final ComboMapper comboMapper;

    @Override
    public List<Combo> getCombosByBMIStatus(BMIStatus status) {
        return comboRepository.findByBmiStatus(status);
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
