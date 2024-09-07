package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateDocumentationImageRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import com.enigma.Instructor_Led.entity.Schedule;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    ScheduleResponse create(CreateScheduleRequest request);
    ScheduleResponse update(UpdateScheduleRequest request);
    Schedule getById(String id);
    List<ScheduleResponse> getAll(String language, String startDate, String endDate);
    List<ScheduleResponse> getAllByTraineeId(String id);
    List<ScheduleResponse> getAllByTrainerId(String id);
    void delete(String id);
}
