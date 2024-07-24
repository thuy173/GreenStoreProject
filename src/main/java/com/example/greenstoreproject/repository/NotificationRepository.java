package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, PagingAndSortingRepository<Notification, Long> {
    List<Notification> findByCustomerId(Long customerId);

    @Query("SELECT n FROM Notification n WHERE n.createAt < :dateTime")
    List<Notification> findByCreatedDateBefore(@Param("dateTime") LocalDateTime dateTime);
}
