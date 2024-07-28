package com.example.greenstoreproject.bean.response.combo;

import lombok.Data;

@Data
public class ComboProductResponse {
    private Long comboProductId;
    private Long productId;
    private String productName;
    private Integer quantity;
}
