package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.plan.PlanRequest;
import com.example.greenstoreproject.bean.response.plan.PlanResponse;
import com.example.greenstoreproject.entity.Combo;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.Plan;
import com.example.greenstoreproject.entity.Schedule;
import com.example.greenstoreproject.repository.ComboRepository;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.repository.ScheduleRepository;
import com.example.greenstoreproject.service.EmailService;
import com.example.greenstoreproject.service.PlanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
@SecurityRequirement(name = "bearerAuth")
public class PlanController {
    private final PlanService planService;
    private final EmailService emailService;
    private final CustomerRepository customerRepository;
    private final ComboRepository comboRepository;
    private final ScheduleRepository scheduleRepository;

    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@RequestBody PlanRequest planRequest) {
        Customers customer = customerRepository.findById(planRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        PlanResponse plan = planService.createPlan(planRequest);

        emailService.sendEmail(customer.getEmail(), "Confirm Create Plan", "Your plan has been successfully created.");

        return ResponseEntity.ok(plan);
    }

    @Scheduled(cron = "0 49 17 * * *")
    @Transactional
    public void sendDailyEmails() {
        List<Schedule> schedules = scheduleRepository.findByDate(LocalDate.now());
        for (Schedule schedule : schedules) {
            emailService.sendDailyScheduleEmail(schedule.getPlan().getCustomer(), schedule);
        }
    }
}
