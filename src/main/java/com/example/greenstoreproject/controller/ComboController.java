package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.combo.ComboRequest;
import com.example.greenstoreproject.bean.response.combo.ComboResponse;
import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.entity.Combo;
import com.example.greenstoreproject.service.ComboService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/combo")
@SecurityRequirement(name = "bearerAuth")
public class ComboController {
    private final ComboService comboService;

    @PostMapping
    public ResponseEntity<Combo> createCombo(@RequestBody ComboRequest request) {
        Combo combo = comboService.createCombo(request);
        return ResponseEntity.ok(combo);
    }

    @GetMapping("/byBmi")
    public ResponseEntity<List<ComboResponse>> getCombosByBMI(@RequestParam BMIStatus bmiStatus) {
        List<ComboResponse> combos = comboService.getCombosByBMIStatus(bmiStatus);
        return ResponseEntity.ok(combos);
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<Combo>> getAllCombos() {
//        List<Combo> combos = comboService.getAllCombos();
//        return ResponseEntity.ok(combos);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ComboResponse> getComboById(@PathVariable Integer id) {
        ComboResponse combo = comboService.getComboById(id);
        return ResponseEntity.ok(combo);
    }

//    @PutMapping("/update/{comboId}")
//    public ResponseEntity<Combo> updateCombo(@PathVariable Long comboId, @RequestBody ComboRequest request) {
//        Combo updatedCombo = comboService.updateCombo(
//                comboId,
//                request.getComboName(),
//                request.getDescription(),
//                request.getBmiStatus(),
//                request.getProducts()
//        );
//        return ResponseEntity.ok(updatedCombo);
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Integer id) {
        comboService.deleteCombo(id);
        return ResponseEntity.ok().build();
    }
}
