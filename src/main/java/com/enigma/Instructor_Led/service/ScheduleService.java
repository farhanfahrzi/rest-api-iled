package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    ScheduleResponse create(CreateScheduleRequest createScheduleRequest);
    ScheduleResponse update(UpdateScheduleRequest updateScheduleRequest);
    ScheduleResponse getById(String id);
    List<ScheduleResponse> getAll();
    void delete(String id);

}
