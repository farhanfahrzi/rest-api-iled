package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateAttendanceRequest;
import com.enigma.Instructor_Led.dto.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {
    AttendanceResponse create(CreateAttendanceRequest attendanceRequest);
    AttendanceResponse getById(String id);
//    AttendanceResponse update(AttendanceRequest attendanceRequest);
    List<AttendanceResponse> getAll();
    void delete(String Id);
}
