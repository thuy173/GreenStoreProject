package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.product.productId = :productId")
    List<Review> findByProductId(@Param("productId") Long productId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r WHERE r.customer.customerId = :customerId AND r.product.productId = :productId AND r.order.orderId = :orderId")
    boolean existsByCustomer_IdAndProduct_IdAndOrder_Id(@Param("customerId") Long customerId, @Param("productId") Long productId, @Param("orderId") Long orderId);
}
