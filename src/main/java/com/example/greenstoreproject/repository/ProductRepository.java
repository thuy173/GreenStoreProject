package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    @Query("SELECT p FROM Products p WHERE p.status = 1")
    List<Products> findActiveProducts();

    @Query("SELECT p FROM Products p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Products> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Products p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Products> findByPriceBetween(Double minPrice, Double maxPrice);

}
