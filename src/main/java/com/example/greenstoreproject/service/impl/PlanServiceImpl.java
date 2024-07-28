package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.plan.PlanRequest;
import com.example.greenstoreproject.bean.request.plan.ScheduleProductRequest;
import com.example.greenstoreproject.bean.request.plan.ScheduleRequest;
import com.example.greenstoreproject.bean.response.plan.PlanResponse;
import com.example.greenstoreproject.entity.*;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.PlanMapper;
import com.example.greenstoreproject.repository.*;
import com.example.greenstoreproject.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final PlanMapper planMapper;
    private final CustomerRepository customerRepository;
    private final ComboRepository comboRepository;

    @Override
    public PlanResponse createPlan(PlanRequest planRequest) {
        Plan plan = planMapper.toEntity(planRequest);
        Customers customer = customerRepository.findById(planRequest.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        Combo combo = comboRepository.findById(planRequest.getComboId())
                .orElseThrow(() -> new NotFoundException("Combo not found"));
        plan.setCustomer(customer);
        plan.setCombo(combo);

        LocalDate endDate = planRequest.getStartDate().plusDays(combo.getDuration());
        plan.setEndDate(endDate);

        for (ScheduleRequest scheduleDTO : planRequest.getSchedules()) {
            Schedule schedule = new Schedule();
            schedule.setDate(scheduleDTO.getDate());
            schedule.setPlan(plan);
            for (ScheduleProductRequest productDTO : scheduleDTO.getScheduledProducts()) {
                ComboProduct comboProduct = combo.getComboProducts().stream()
                        .filter(cp -> cp.getComboProductId().equals(productDTO.getComboProductId()))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("ComboProduct not found"));
                ScheduleProduct scheduledProduct = new ScheduleProduct();
                scheduledProduct.setComboProduct(comboProduct);
                scheduledProduct.setQuantity(productDTO.getQuantity());
                scheduledProduct.setDayInRegimen(productDTO.getDayInRegimen());
                scheduledProduct.setSchedule(schedule);
                schedule.getScheduledProducts().add(scheduledProduct);
            }
            plan.getSchedules().add(schedule);
        }
        plan = planRepository.save(plan);
        return planMapper.toResponseDTO(plan);
    }

}
