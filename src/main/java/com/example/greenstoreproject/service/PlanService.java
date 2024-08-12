package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.plan.PlanRequest;
import com.example.greenstoreproject.bean.response.plan.PlanResponse;
import com.example.greenstoreproject.bean.response.plan.ScheduleResponse;
import com.example.greenstoreproject.entity.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface PlanService {
    PlanResponse createPlan(PlanRequest planRequest);

    List<ScheduleResponse> getSchedulesByDate(LocalDate date);

}
