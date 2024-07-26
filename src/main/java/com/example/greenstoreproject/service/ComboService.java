package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.combo.ComboProductRequest;
import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.entity.Combo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComboService {
    List<Combo> getCombosByBMIStatus(BMIStatus status);
    Combo createCombo(String comboName, String description, BMIStatus status, List<ComboProductRequest> products);
    void deleteCombo(Integer comboId);
}
