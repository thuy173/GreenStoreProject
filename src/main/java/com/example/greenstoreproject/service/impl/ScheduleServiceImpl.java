package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.entity.Plan;
import com.example.greenstoreproject.entity.Schedule;
import com.example.greenstoreproject.repository.PlanRepository;
import com.example.greenstoreproject.repository.ScheduleRepository;
import com.example.greenstoreproject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PlanRepository planRepository;

    @Override
    public void saveSchedule(Schedule schedule) {
        Plan plan = schedule.getPlan();
        if (plan.getPlanId() == null) {
            planRepository.save(plan);
        }
        scheduleRepository.save(schedule);
    }
}
