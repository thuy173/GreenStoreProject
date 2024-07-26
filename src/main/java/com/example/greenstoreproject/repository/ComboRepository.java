package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.BMIStatus;
import com.example.greenstoreproject.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Integer> {
    List<Combo> findByBmiStatus(BMIStatus bmiStatus);
}
