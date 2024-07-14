package com.example.greenstoreproject.event;

import com.example.greenstoreproject.entity.Orders;
import org.springframework.context.ApplicationEvent;

public class NewOrderEvent extends ApplicationEvent {

    private final Orders order;

    public NewOrderEvent(Object source, Orders order) {
        super(source);
        this.order = order;
    }

    public Orders getOrder() {
        return order;
    }

}
