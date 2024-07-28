package com.example.greenstoreproject.bean.request.plan;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PlanRequest {
    private Long customerId;
    private int comboId;
    private LocalDate startDate;
    private List<ScheduleRequest> schedules;
}
