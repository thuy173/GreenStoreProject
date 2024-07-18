package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.response.notification.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService{
    Page<NotificationResponse> getNotifications(int page, int size);
}
