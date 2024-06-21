package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.nutrient.NutrientRequest;
import com.example.greenstoreproject.bean.response.nutrient.NutrientResponse;
import com.example.greenstoreproject.entity.Nutrients;
import com.example.greenstoreproject.exception.EmptyException;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.NutrientMapper;
import com.example.greenstoreproject.repository.NutrientRepository;
import com.example.greenstoreproject.service.NutrientService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NutrientServiceImpl implements NutrientService {
    private final NutrientRepository nutrientRepository;

    @Override
    public List<NutrientResponse> getAllNutrient() {
        List<Nutrients> nutrients = nutrientRepository.findAll();
        if(nutrients.isEmpty()){
            throw new EmptyException("Nutrient list is empty");
        }
        return nutrients.stream()
                .map(NutrientMapper::convertToNutrientResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NutrientResponse getNutrientById(Long id) {
        Nutrients nutrients = nutrientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nutrient not found " + id));
        return NutrientMapper.convertToNutrientResponse(nutrients);
    }

    @Override
    public String createNutrient(NutrientRequest nutrientRequest) {
        Nutrients nutrients = NutrientMapper.convertToNutrientEntity(nutrientRequest);
        nutrientRepository.save(nutrients);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    @Override
    public String updateNutrient(Long id, NutrientRequest nutrientRequest) {
        Nutrients nutrients = nutrientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nutrient not found " + id));
        NutrientMapper.updateNutrientFromRequest(nutrients, nutrientRequest);
        nutrientRepository.save(nutrients);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }

    @Override
    public String deleteNutrient(Long id) {
        Nutrients nutrients = nutrientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nutrient not found " + id));
        nutrientRepository.delete(nutrients);
        return SuccessMessage.SUCCESS_DELETED.getMessage();
    }
}
