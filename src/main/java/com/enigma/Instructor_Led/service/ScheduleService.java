package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import com.enigma.Instructor_Led.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule create(CreateScheduleRequest createScheduleRequest);
    ScheduleResponse update(UpdateScheduleRequest updateScheduleRequest);
    Schedule getById(String id);
    ScheduleResponse getOneById(String id);
    List<ScheduleResponse> getAll();
    void delete(String id);

}
