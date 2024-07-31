package com.example.greenstoreproject.service;

import com.example.greenstoreproject.entity.Advice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdviceService {
    List<Advice> getAllAdvice();
    Advice createAdvice(Advice advice);
    Advice updateAdvice(int adviceId, Advice newAdviceData);
    void deleteAdvice(int adviceId);
}
