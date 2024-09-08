package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateAttendanceRequest;
import com.enigma.Instructor_Led.dto.response.AttendanceResponse;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PathUrl.ATTENDANCE)
public class AttendanceController {

    private final AttendanceService attendanceService;

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
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<AttendanceResponse>>> getAllAttendance(){
        List<AttendanceResponse> attendances = attendanceService.getAll();
        CommonResponse<List<AttendanceResponse>> response = CommonResponse
                .<List<AttendanceResponse>>builder()
                .message("Attendance fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(attendances)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

