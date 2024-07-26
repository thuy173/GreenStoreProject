package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Combo;
import com.example.greenstoreproject.entity.ComboProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboProductRepository extends JpaRepository<ComboProduct, Long> {
    List<ComboProduct> findByCombo(Combo combo);
}
