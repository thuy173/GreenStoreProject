package com.example.greenstoreproject.bean.response.plan;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScheduleResponse {
    private Long scheduleId;
    private LocalDate date;
    private List<ScheduleProductResponse> scheduledProducts;
}
