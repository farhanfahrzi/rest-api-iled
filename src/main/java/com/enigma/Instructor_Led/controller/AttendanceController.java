package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateAttendanceRequest;
import com.enigma.Instructor_Led.dto.response.AttendanceResponse;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PathUrl.ATTENDANCE)
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @PostMapping
    public ResponseEntity<CommonResponse<AttendanceResponse>> createNewAttendance(
            @RequestBody CreateAttendanceRequest attendanceRequest
    ){
        AttendanceResponse attendance = attendanceService.create(attendanceRequest);
        CommonResponse<AttendanceResponse> response = CommonResponse
                .<AttendanceResponse>builder()
                .message("Attendance created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(attendance)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @GetMapping
    public List<AttendanceResponse> getAllAttendance(){
        return attendanceService.getAll();
    }

}

