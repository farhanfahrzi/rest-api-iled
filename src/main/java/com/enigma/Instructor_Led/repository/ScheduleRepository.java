package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
}
