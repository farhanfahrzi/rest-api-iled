package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateDocumentationImageRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import com.enigma.Instructor_Led.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    ScheduleResponse create(CreateScheduleRequest request);
    ScheduleResponse update(UpdateScheduleRequest request);
   // ScheduleResponse updateDocumentation(UpdateDocumentationImageRequest request);
    Schedule getById(String id);
    List<Schedule> getAll();
    Page<Schedule> getAll(Integer size, Integer page, String language, String startDate, String endDate);
    Page<ScheduleResponse> getAllByTraineeId(Integer page, Integer size, String id);
    Page<ScheduleResponse> getAllByTrainerId(Integer page, Integer size, String id);
    void delete(String id);
}
