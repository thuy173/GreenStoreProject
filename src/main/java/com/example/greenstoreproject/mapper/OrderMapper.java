package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.order.OrderRequest;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.bean.response.orderItem.OrderItemResponse;
import com.example.greenstoreproject.entity.OrderItems;
import com.example.greenstoreproject.entity.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    public OrderResponse convertToResponse(Orders order) {
        OrderResponse orderResponseDTO = new OrderResponse();
        orderResponseDTO.setOrderId(order.getOrderId());
        orderResponseDTO.setCustomerId(order.getCustomer().getCustomerId());
        orderResponseDTO.setOrderDate(order.getOrderDate());
        orderResponseDTO.setDiscount(order.getDiscount());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setLatitude(order.getLatitude());
        orderResponseDTO.setLongitude(order.getLongitude());
        orderResponseDTO.setShippingAddress(order.getShippingAddress());

        List<OrderItemResponse> orderItems = order.getOrderItems().stream().map(orderItem -> {
            OrderItemResponse orderItemResponseDTO = new OrderItemResponse();
            orderItemResponseDTO.setOrderItemId(orderItem.getOrderItemId());
            orderItemResponseDTO.setProductId(orderItem.getProduct().getProductId());
            orderItemResponseDTO.setQuantity(orderItem.getQuantity());
            return orderItemResponseDTO;
        }).collect(Collectors.toList());

        orderResponseDTO.setOrderItems(orderItems);
        return orderResponseDTO;
    }

    public Orders convertToEntity(OrderRequest orderRequest) {
        Orders order = new Orders();
        order.setStatus(orderRequest.getStatus());
        order.setTotalAmount(orderRequest.getTotalAmount());

        return order;
    }


}
