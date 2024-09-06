package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
}
