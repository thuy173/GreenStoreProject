package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.entity.Advice;
import com.example.greenstoreproject.service.AdviceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advice")
@SecurityRequirement(name = "bearerAuth")
public class AdviceController {
    private final AdviceService adviceService;

    @GetMapping
    public List<Advice> getAllAdvice() {
        return adviceService.getAllAdvice();
    }

    @PostMapping
    public ResponseEntity<Advice> createAdvice(@RequestBody Advice advice) {
        Advice createdAdvice = adviceService.createAdvice(advice);
        return ResponseEntity.ok(createdAdvice);
    }

    @PutMapping("/{adviceId}")
    public ResponseEntity<Advice> updateAdvice(@PathVariable int adviceId, @RequestBody Advice newAdviceData) {
        Advice updatedAdvice = adviceService.updateAdvice(adviceId, newAdviceData);
        return ResponseEntity.ok(updatedAdvice);
    }

    @DeleteMapping("/{adviceId}")
    public ResponseEntity<Void> deleteAdvice(@PathVariable int adviceId) {
        adviceService.deleteAdvice(adviceId);
        return ResponseEntity.noContent().build();
    }
}
