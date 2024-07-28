package com.example.greenstoreproject.bean.response.plan;

import lombok.Data;

@Data
public class ScheduleProductResponse {
    private Long scheduledProductId;
    private Long comboProductId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer dayInRegimen;

}
