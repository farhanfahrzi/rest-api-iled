package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.entity.AttendanceDetail;
import com.enigma.Instructor_Led.repository.AttendanceDetailRepository;
import com.enigma.Instructor_Led.service.AttendanceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceDetailServiceImpl implements AttendanceDetailService {

    private final AttendanceDetailRepository attendanceDetailRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<AttendanceDetail> createBulk(List<AttendanceDetail> attendanceDetails) {
        return attendanceDetailRepository.saveAllAndFlush(attendanceDetails);
    }
}
