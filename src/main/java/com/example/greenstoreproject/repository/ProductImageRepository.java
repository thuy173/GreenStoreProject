package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {
}
