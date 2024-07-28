package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.plan.PlanRequest;
import com.example.greenstoreproject.bean.response.plan.PlanResponse;
import com.example.greenstoreproject.entity.Combo;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.Plan;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface PlanService {
    PlanResponse createPlan(PlanRequest planRequest);
}
