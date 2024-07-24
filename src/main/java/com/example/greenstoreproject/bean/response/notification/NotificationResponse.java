package com.example.greenstoreproject.bean.response.notification;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {

    private Long notificationId;

    private Long customerId;

    private Long orderId;

    private String avatar;

    private String fullName;

    private String orderCode;

    private Long blogId;

    private String title;

    private String thumbnail;

    private LocalDateTime createAt;
}
