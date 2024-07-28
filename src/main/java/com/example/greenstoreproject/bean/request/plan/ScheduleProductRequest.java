package com.example.greenstoreproject.bean.request.plan;

import lombok.Data;

@Data
public class ScheduleProductRequest {
    private Long comboProductId;
    private Integer quantity;
    private Integer dayInRegimen;

}
