package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Plan;
import com.example.greenstoreproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.date = :date")
    List<Schedule> findSchedulesByDate(@Param("date") LocalDate date);
}
