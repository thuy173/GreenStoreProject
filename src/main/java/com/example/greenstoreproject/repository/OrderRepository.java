package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o.status FROM Orders o WHERE o.orderId = :orderId")
    String findOrderStatusByOrderId(@Param("orderId") Long orderId);

    List<Orders> findByCustomerCustomerId(Long customerId);
}
