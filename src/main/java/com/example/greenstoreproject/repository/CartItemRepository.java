package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.CartItems;
import com.example.greenstoreproject.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    void deleteByProduct(Products product);
}
