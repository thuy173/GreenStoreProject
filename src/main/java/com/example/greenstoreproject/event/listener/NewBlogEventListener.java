package com.example.greenstoreproject.event.listener;

import com.example.greenstoreproject.bean.response.blog.BlogResponse;
import com.example.greenstoreproject.entity.Blog;
import com.example.greenstoreproject.entity.Notification;
import com.example.greenstoreproject.event.NewBlogEvent;
import com.example.greenstoreproject.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewBlogEventListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    @EventListener
    public void handleNewBlogEvent(NewBlogEvent event) {
        Blog blog = event.getBlog();

        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setBlogId(blog.getBlogId());
        blogResponse.setTitle(blog.getTitle());
        blogResponse.setThumbnail(blog.getThumbnail());

        Notification notification = new Notification();
        notification.setCustomerId(blog.getCustomer().getCustomerId());
        notification.setBlogId(blog.getBlogId());
        notification.setCreateAt(blog.getCreatedAt());
        notificationRepository.save(notification);


        messagingTemplate.convertAndSend("/topic/blogs", blogResponse);
    }
}
