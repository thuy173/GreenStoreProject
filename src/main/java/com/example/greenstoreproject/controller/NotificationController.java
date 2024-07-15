package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.response.blog.BlogResponse;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.entity.Blog;
import com.example.greenstoreproject.entity.Notification;
import com.example.greenstoreproject.repository.NotificationRepository;
import com.example.greenstoreproject.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;


    @MessageMapping("/newOrder")
    @SendTo("/topic/orders")
    public List<OrderResponse> notifyNewOrder(OrderResponse orderResponse) {


        List<Notification> notifications = notificationRepository.findByCustomerId(orderResponse.getCustomerId());
        List<OrderResponse> responses = notifications.stream()
                .map(notification -> {
                    OrderResponse response = new OrderResponse();
                    response.setOrderId(notification.getOrderId());
                    return response;
                })
                .collect(Collectors.toList());

        return responses;
    }

    @MessageMapping("/newBlog")
    @SendTo("/topic/blogs")
    public List<BlogResponse> notifyNewBlog(Blog blog) {

        List<Notification> notifications = notificationRepository.findByCustomerId(blog.getCustomer().getCustomerId());
        List<BlogResponse> responses = notifications.stream()
                .map(notification -> {
                    BlogResponse response = new BlogResponse();
                    response.setBlogId(notification.getBlogId());
                    return response;
                })
                .collect(Collectors.toList());

        return responses;
    }

    @GetMapping("/api/notification")
    public Page<Notification> getNotifications(@RequestParam int page, @RequestParam int size) {
        return notificationService.getNotifications(page, size);
    }
}
