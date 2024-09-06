package com.enigma.Instructor_Led.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.repository.ScheduleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponse create(CreateScheduleRequest createScheduleRequest) {
        Schedule schedule = new Schedule();
        schedule.setId(createScheduleRequest.getId());
        schedule.setName(createScheduleRequest.getName());
        // Set other fields as necessary
        schedule = scheduleRepository.save(schedule);
        return convertToResponse(schedule);
    }

    @Override
    public ScheduleResponse update(UpdateScheduleRequest updateScheduleRequest) {
        Schedule schedule = scheduleRepository.findById(updateScheduleRequest.getId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        schedule.setName(updateScheduleRequest.getName());
        // Update other fields as necessary
        schedule = scheduleRepository.save(schedule);
        return convertToResponse(schedule);
    }

    @Override
    public ScheduleResponse getById(String id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return convertToResponse(schedule);
    }

    @Override
    public List<ScheduleResponse> getAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }

    private ScheduleResponse convertToResponse(Schedule schedule) {
        ScheduleResponse response = new ScheduleResponse();
        response.setId(schedule.getId());
        response.setName(schedule.getName());
        // Set other fields as necessary
        return response;
    }
}
