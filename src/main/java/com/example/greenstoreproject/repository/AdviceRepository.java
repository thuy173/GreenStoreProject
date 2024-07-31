package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Advice;
import com.example.greenstoreproject.entity.BMIStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Integer> {
    List<Advice> findAllByStatus(BMIStatus status);
}
