package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {
    Carts findByCustomerCustomerId (Long customerId);
}
