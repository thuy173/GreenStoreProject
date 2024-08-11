package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.combo.ComboProductRequest;
import com.example.greenstoreproject.bean.request.combo.ComboRequest;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.combo.ComboResponse;
import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.entity.Combo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComboService {
    List<ComboResponse> getCombosByBMIStatus(BMIStatus status);
    ComboResponse getComboById(Integer id);
    Combo createCombo(ComboRequest comboRequest);
    void deleteCombo(Integer comboId);
}
