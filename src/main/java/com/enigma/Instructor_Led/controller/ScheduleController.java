package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateDocumentationImageRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.dto.response.PagingResponse;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.service.ImageKitService;
import com.enigma.Instructor_Led.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ImageKitService imageKitService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse<ScheduleResponse>> createSchedule(
            @RequestBody CreateScheduleRequest request
    ) {
        ScheduleResponse schedule = scheduleService.create(request);
        CommonResponse<ScheduleResponse> response = CommonResponse
                .<ScheduleResponse>builder()
                .message("Schedule created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(schedule)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<CommonResponse<ScheduleResponse>> updateSchedule(
            @RequestBody UpdateScheduleRequest request
    ) {
        ScheduleResponse schedule = scheduleService.update(request);
        CommonResponse<ScheduleResponse> response = CommonResponse
                .<ScheduleResponse>builder()
                .message("Schedule updated successfully")
                .statusCode(HttpStatus.OK.value())
                .data(schedule)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    @PutMapping(path = "/docs")
    public ResponseEntity<CommonResponse<ScheduleResponse>> updateDocumentation(
            @RequestBody UpdateDocumentationImageRequest request
    ) {
        ScheduleResponse schedule = scheduleService.updateDocumentation(request);
        CommonResponse<ScheduleResponse> response = CommonResponse
                .<ScheduleResponse>builder()
                .message("Schedule updated successfully")
                .statusCode(HttpStatus.OK.value())
                .data(schedule)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    @PostMapping(path = "/docs-upload", consumes = {"multipart/form-data"})
    public ResponseEntity<CommonResponse<DocumentationImageResponse>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") String id

    ) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Handle case when the file is empty
        }
        // DocumentationImageResponse imageResponse = imageKitService.uploadImage(file, id);
        DocumentationImageResponse imageResponse = imageKitService.uploadDocs(file);
        CommonResponse<DocumentationImageResponse> response = CommonResponse
                .<DocumentationImageResponse>builder()
                .message("Documentation uploaded successfully")
                .statusCode(HttpStatus.OK.value())
                .data(imageResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<ScheduleResponse>> getById(
            @PathVariable("id") String id
    ) {
        Schedule schedule = scheduleService.getById(id);
        ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                .id(schedule.getId())
                .date(schedule.getDate())
                .topic(schedule.getTopic())
                .trainerId(schedule.getTrainer().getId())
                .programmingLanguageId(schedule.getProgrammingLanguage().getId())
                .build();
        CommonResponse<ScheduleResponse> response = CommonResponse
                .<ScheduleResponse>builder()
                .message("Schedule fetched succesfully")
                .statusCode(HttpStatus.OK.value())
                .data(scheduleResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<ScheduleResponse>>> getAllSchedules(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "start-date", required = false) String startDate,
            @RequestParam(name = "end-date", required = false) String endDate
    ) {
        Page<Schedule> pagedSchedules = scheduleService.getAll(size, page, language, startDate, endDate);

        List<ScheduleResponse> schedules = pagedSchedules.stream().map(
                schedule -> ScheduleResponse.builder()
                        .id(schedule.getId())
                        .date(schedule.getDate())
                        .topic((schedule.getTopic()))
                        .trainerId((schedule.getTrainer().getId()))
                        .documentationImages(schedule.getDocumentationImages().stream().map(
                                doc -> DocumentationImageResponse.builder()
                                        .id(doc.getId())
                                        .link(doc.getLink())
                                        .build()
                        ).toList())
                        .build()
        ).toList();

        CommonResponse<List<ScheduleResponse>> response = CommonResponse
                .<List<ScheduleResponse>>builder()
                .message("Schedule fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(schedules)
                .paging(PagingResponse.builder()
                        .totalPages(pagedSchedules.getTotalPages())
                        .totalElement(pagedSchedules.getTotalElements())
                        .page(page)
                        .size(size)
                        .hasNext(pagedSchedules.hasNext())
                        .hasPrevious(pagedSchedules.hasPrevious())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/trainee/{id}")
    public ResponseEntity<CommonResponse<List<ScheduleResponse>>> getAllByTraineeId(
            @PathVariable String id
    ) {
        List<ScheduleResponse> schedules = scheduleService.getAllByTraineeId(id);
        CommonResponse<List<ScheduleResponse>> response = CommonResponse
                .<List<ScheduleResponse>>builder()
                .message("Schedule fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(schedules)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/trainer/{id}")
    public ResponseEntity<CommonResponse<List<ScheduleResponse>>> getAllByTrainerId(
            @PathVariable String id
    ) {
        List<ScheduleResponse> schedules = scheduleService.getAllByTrainerId(id);
        CommonResponse<List<ScheduleResponse>> response = CommonResponse
                .<List<ScheduleResponse>>builder()
                .message("Schedule fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(schedules)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<?>> deleteSchedule(
            @PathVariable String id
    ) {
        scheduleService.delete(id);
        CommonResponse<?> response = CommonResponse
                .builder()
                .message("Schedule deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
