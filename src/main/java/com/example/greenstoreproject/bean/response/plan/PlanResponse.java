package com.example.greenstoreproject.bean.response.plan;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PlanResponse {
    private Long planId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ScheduleResponse> schedules;
}
