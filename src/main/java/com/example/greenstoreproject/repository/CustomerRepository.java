package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {
    Customers findByEmail(String email);
    boolean existsByEmail(String email);
}
