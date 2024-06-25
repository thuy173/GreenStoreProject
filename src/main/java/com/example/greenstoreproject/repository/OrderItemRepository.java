package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
    void deleteByOrder_OrderId(Long orderId);
}
