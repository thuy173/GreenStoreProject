package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.entity.Notification;
import com.example.greenstoreproject.repository.NotificationRepository;
import com.example.greenstoreproject.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public Page<Notification> getNotifications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findAll(pageable);
    }
}
