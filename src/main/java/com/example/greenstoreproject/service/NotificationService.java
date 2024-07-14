package com.example.greenstoreproject.service;

import com.example.greenstoreproject.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService{
    Page<Notification> getNotifications(int page, int size);
}
