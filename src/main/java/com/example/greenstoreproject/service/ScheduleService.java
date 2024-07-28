package com.example.greenstoreproject.service;

import com.example.greenstoreproject.entity.Schedule;
import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {
    void saveSchedule(Schedule schedule);
}
