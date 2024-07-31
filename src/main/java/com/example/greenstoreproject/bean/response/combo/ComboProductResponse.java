package com.example.greenstoreproject.bean.response.combo;

import com.example.greenstoreproject.bean.response.product.ProductResponse;
import lombok.Data;

import java.util.List;

@Data
public class ComboProductResponse {
    private Long comboProductId;
    private List<ProductResponse> products;
    private Double quantity;
}
