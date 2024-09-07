package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateAttendanceRequest;
import com.enigma.Instructor_Led.dto.response.AttendanceResponse;
import com.enigma.Instructor_Led.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PathUrl.ATTENDANCE)
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public AttendanceResponse createNewAttendance(
            @RequestBody CreateAttendanceRequest attendanceRequest
    ){
        return attendanceService.create(attendanceRequest);
    }

    @GetMapping
    public List<AttendanceResponse> getAllAttendance(){
        return attendanceService.getAll();
    }

}

