package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
    void deleteByOrder_OrderId(Long orderId);

    @Query("SELECT CASE WHEN COUNT(oi) > 0 THEN true ELSE false END FROM OrderItems oi WHERE oi.order.orderId = :orderId AND oi.product.productId = :productId")
    boolean existsByOrderIdAndProductId(@Param("orderId") Long orderId, @Param("productId") Long productId);

}
