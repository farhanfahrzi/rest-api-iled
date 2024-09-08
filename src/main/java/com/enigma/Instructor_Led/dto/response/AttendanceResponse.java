package com.enigma.Instructor_Led.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceResponse {
    private String id;

    private ScheduleResponse schedule;

    private Date attendanceDate;

    private List<AttendanceDetailResponse> attendanceDetails;
}
