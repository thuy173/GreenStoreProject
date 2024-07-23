package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long>, PagingAndSortingRepository<Products, Long> {
    @Query("SELECT p FROM Products p WHERE p.status = 1")
    Page<Products> findActiveProducts(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE " +
            "(:name IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:category IS NULL OR LOWER(p.category.categoryName) = LOWER(:category)) " +
            "ORDER BY p.productName ASC")
    Page<Products> searchProducts(
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("category") String category,
            Pageable pageable
    );

}
