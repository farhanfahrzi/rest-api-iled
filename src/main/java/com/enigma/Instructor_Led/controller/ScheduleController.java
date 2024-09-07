package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PathUrl.SCHEDULE)
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CommonResponse<Schedule>> createNewSchedule(@RequestBody CreateScheduleRequest schedule) {
        Schedule newSchedule = scheduleService.create(schedule);

        CommonResponse<Schedule> commonResponse = CommonResponse.<Schedule>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully Create New Schedule")
                .data(newSchedule)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

}
