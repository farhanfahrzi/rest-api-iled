package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.entity.AttendanceDetail;

import java.util.List;

public interface AttendanceDetailService {

    List<AttendanceDetail> createBulk(List<AttendanceDetail> attendanceDetails);
}
