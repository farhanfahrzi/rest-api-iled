package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.entity.AttendanceDetail;
import com.enigma.Instructor_Led.repository.AttendanceDetailRepository;
import com.enigma.Instructor_Led.service.AttendanceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceDetailServiceImpl implements AttendanceDetailService {

    private final AttendanceDetailRepository attendanceDetailRepository;

    @Override
    public List<AttendanceDetail> createBulk(List<AttendanceDetail> attendanceDetails) {
        return attendanceDetailRepository.saveAllAndFlush(attendanceDetails);
    }
}
