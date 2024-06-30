package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r WHERE r.product.productId = :productId")
    List<Rating> findByProductId(@Param("productId") Long productId);

}
