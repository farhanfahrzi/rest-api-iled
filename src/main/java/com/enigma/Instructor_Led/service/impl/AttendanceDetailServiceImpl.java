package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.entity.AttendanceDetail;
import com.enigma.Instructor_Led.repository.AttendanceDetailRepository;
import com.enigma.Instructor_Led.service.AttendanceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceDetailServiceImpl implements AttendanceDetailService {

    @Autowired
    private AttendanceDetailRepository attendanceDetailRepository;

    @Override
    public List<AttendanceDetail> createBulk(List<AttendanceDetail> attendanceDetails) {
        return attendanceDetailRepository.saveAll(attendanceDetails);
    }
}
