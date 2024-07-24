package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.response.notification.NotificationResponse;
import com.example.greenstoreproject.entity.Blog;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.Notification;
import com.example.greenstoreproject.entity.Orders;
import com.example.greenstoreproject.repository.BlogRepository;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    private final OrderRepository orderRepository;
    private final BlogRepository blogRepository;
    private final CustomerRepository customerRepository;

    public NotificationResponse toResponse(Notification notification) {
        NotificationResponse dto = new NotificationResponse();
        dto.setOrderId(notification.getOrderId());
        dto.setCustomerId(notification.getCustomerId());
        dto.setNotificationId(notification.getNotificationId());
        dto.setBlogId(notification.getBlogId());
        dto.setCreateAt(notification.getCreateAt());

        if (notification.getOrderId() != null) {
            Orders order = orderRepository.findById(notification.getOrderId())
                    .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + notification.getOrderId()));

            dto.setFullName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
            dto.setOrderCode(order.getOrderCode());

            Customers customers = customerRepository.findById(notification.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + order.getCustomer().getCustomerId()));
            dto.setAvatar(customers.getAvatar());
        }

        if (notification.getBlogId() != null) {
            Blog blog = blogRepository.findById(notification.getBlogId())
                    .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + notification.getBlogId()));

            dto.setTitle(blog.getTitle());
            dto.setThumbnail(blog.getThumbnail());
        }

        return dto;
    }
}
