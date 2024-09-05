package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
}
