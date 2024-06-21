package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Nutrients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutrientRepository extends JpaRepository<Nutrients, Long> {
}
