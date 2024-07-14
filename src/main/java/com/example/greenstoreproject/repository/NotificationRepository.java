package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, PagingAndSortingRepository<Notification, Long> {
    List<Notification> findByCustomerId(Long customerId);
}
