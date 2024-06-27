package com.example.greenstoreproject.bean.request.orderItem;

import com.example.greenstoreproject.entity.Orders;
import com.example.greenstoreproject.entity.Products;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class OrderItemRequest {

    private Long productId;

    private Double quantity;

}
