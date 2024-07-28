package com.example.greenstoreproject.bean.request.plan;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScheduleRequest {
    private LocalDate date;
    private List<ScheduleProductRequest> scheduledProducts;
}
