package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.orderItem.OrderItemRequest;
import com.example.greenstoreproject.bean.response.orderItem.OrderItemResponse;
import com.example.greenstoreproject.bean.response.product.ProductResponse;
import com.example.greenstoreproject.entity.OrderItems;
import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapper {
    private final ProductRepository productRepository;

    public OrderItems toEntity(OrderItemRequest orderItemRequest) {
        OrderItems orderItem = new OrderItems();
        // Tìm sản phẩm từ cơ sở dữ liệu và set cho order item
        Products product = productRepository.findById(orderItemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        orderItem.setQuantity(orderItemRequest.getQuantity());
        return orderItem;
    }

    public OrderItemResponse toResponse(OrderItems orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setOrderItemId(orderItem.getOrderItemId());
        response.setQuantity(orderItem.getQuantity());
        return response;
    }
}
