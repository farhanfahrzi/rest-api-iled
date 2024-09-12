package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.AttendanceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceDetailRepository extends JpaRepository<AttendanceDetail, String> {
}
