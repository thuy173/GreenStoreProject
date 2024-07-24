package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.response.notification.NotificationResponse;
import com.example.greenstoreproject.entity.Notification;
import com.example.greenstoreproject.mapper.NotificationMapper;
import com.example.greenstoreproject.repository.NotificationRepository;
import com.example.greenstoreproject.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Page<NotificationResponse> getNotifications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createAt")));
        Page<Notification> notifications = notificationRepository.findAllNotifications(pageable);

        return notifications.map(notificationMapper::toResponse);
    }

    @Scheduled(cron = "0 0 0 * * ?")// Runs every day at midnight
    public void deleteOldNotifications() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Notification> oldNotifications = notificationRepository.findByCreatedDateBefore(thirtyDaysAgo);
        notificationRepository.deleteAll(oldNotifications);
    }
}
