package com.example.greenstoreproject.event.listener;

import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.entity.Notification;
import com.example.greenstoreproject.entity.Orders;
import com.example.greenstoreproject.event.NewOrderEvent;
import com.example.greenstoreproject.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@Component
@RequiredArgsConstructor
public class NewOrderEventListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;


    @EventListener
    public void handleNewOrderEvent(NewOrderEvent event) {
        Orders order = event.getOrder();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderCode(order.getOrderCode());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setFullName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());


        Notification notification = new Notification();
        notification.setCustomerId(order.getCustomer().getCustomerId());
        notification.setOrderId(order.getOrderId());
        notificationRepository.save(notification);

        messagingTemplate.convertAndSend("/topic/orders", orderResponse);
    }
}
