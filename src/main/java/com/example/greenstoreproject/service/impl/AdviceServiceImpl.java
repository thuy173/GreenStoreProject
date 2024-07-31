package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.entity.Advice;
import com.example.greenstoreproject.repository.AdviceRepository;
import com.example.greenstoreproject.service.AdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdviceServiceImpl implements AdviceService {
    private final AdviceRepository adviceRepository;

    @Override
    public List<Advice> getAllAdvice() {
        return adviceRepository.findAll();
    }

    @Override
    public Advice createAdvice(Advice advice) {
        return adviceRepository.save(advice);
    }

    @Override
    public Advice updateAdvice(int adviceId, Advice newAdviceData) {
        Optional<Advice> optionalAdvice = adviceRepository.findById(adviceId);

        if (optionalAdvice.isPresent()) {
            Advice existingAdvice = optionalAdvice.get();
            existingAdvice.setContent(newAdviceData.getContent());
            existingAdvice.setStatus(newAdviceData.getStatus());
            return adviceRepository.save(existingAdvice);
        } else {
            throw new RuntimeException("Advice not found with id " + adviceId);
        }
    }

    @Override
    public void deleteAdvice(int adviceId) {
        if (adviceRepository.existsById(adviceId)) {
            adviceRepository.deleteById(adviceId);
        } else {
            throw new RuntimeException("Advice not found with id " + adviceId);
        }
    }
}
