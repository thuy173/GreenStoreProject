package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.plan.PlanRequest;
import com.example.greenstoreproject.bean.response.plan.PlanResponse;
import org.springframework.stereotype.Service;

@Service
public interface PlanService {
    PlanResponse createPlan(PlanRequest planRequest);
}
