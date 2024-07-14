package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.entity.Notification;
import com.example.greenstoreproject.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @MessageMapping("/newOrder")
    @SendTo("/topic/orders")
    public List<OrderResponse> notifyNewOrder(OrderResponse orderResponse) {


        List<Notification> notifications = notificationRepository.findByCustomerId(orderResponse.getCustomerId());
        List<OrderResponse> responses = notifications.stream()
                .map(notification -> {
                    OrderResponse response = new OrderResponse();
                    response.setOrderCode(notification.getOrderCode());
                    response.setCustomerId(notification.getCustomerId());
                    response.setFullName(notification.getFullName());
                    response.setOrderDate(notification.getOrderDate());
                    return response;
                })
                .collect(Collectors.toList());

        return responses;
    }
}
