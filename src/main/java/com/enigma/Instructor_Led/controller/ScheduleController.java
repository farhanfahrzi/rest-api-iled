package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateDocumentationImageRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import com.enigma.Instructor_Led.service.ImageKitService;
import com.enigma.Instructor_Led.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ImageKitService imageKitService;

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

    @PostMapping (path = "/docs", consumes = {"multipart/form-data"})
    public ResponseEntity<CommonResponse<DocumentationImageResponse>> updateDocumentation(
            @RequestParam("file") MultipartFile file,
            @RequestParam("data") UpdateDocumentationImageRequest request
    ) {
//        // Validation
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body(
//                    CommonResponse.<DocumentationImageResponse>builder()
//                            .message("File is required")
//                            .statusCode(HttpStatus.BAD_REQUEST.value())
//                            .build()
//            );
//        }

        System.out.println("file : " + file.getOriginalFilename());

        DocumentationImageResponse documentation = scheduleService.updateDocumentation(file, request);
        CommonResponse<DocumentationImageResponse> response = CommonResponse
                .<DocumentationImageResponse>builder()
                .message("Document updated successfully")
                .statusCode(HttpStatus.OK.value())
                .data(documentation)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping (path = "/docs")
//    public ResponseEntity<CommonResponse<DocumentationImageResponse>> updateDocumentation(
//            RequestPart("file") MultipartFile file) {
//        return service.create(UpdateDocumentationImageRequest, file);
//            @RequestPart("data") UpdateDocumentationImageRequest request
//    ) {
//        // Validation
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body(
//                    CommonResponse.<DocumentationImageResponse>builder()
//                            .message("File is required")
//                            .statusCode(HttpStatus.BAD_REQUEST.value())
//                            .build()
//            );
//        }
//
//        DocumentationImageResponse documentation = scheduleService.updateDocumentation(file, request);
//        CommonResponse<DocumentationImageResponse> response = CommonResponse
//                .<DocumentationImageResponse>builder()
//                .message("Document updated successfully")
//                .statusCode(HttpStatus.OK.value())
//                .data(documentation)
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//    @PostMapping("/upload")
//    public ResponseEntity<CommonResponse<DocumentationImageResponse>> uploadDocumentationImage(
//            @RequestPart("file") MultipartFile file,
//            @RequestPart("data") UpdateDocumentationImageRequest request) {
//
//        // Panggil service untuk mengupload image
//        imageKitService.uploadFileToImageKit(file);
//
//        return ResponseEntity.ok(
//                CommonResponse.<DocumentationImageResponse>builder()
//                        .statusCode(HttpStatus.OK.value())
//                        .message("Document updated successfully")
//                        .data(response)
//                        .build()
//        );
//    }


    @GetMapping
    public ResponseEntity<CommonResponse<List<ScheduleResponse>>> getAllSchedules(
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "start-date", required = false) String startDate,
            @RequestParam(name = "end-date", required = false) String endDate
    ) {
        List<ScheduleResponse> schedules = scheduleService.getAll(language, startDate, endDate);
        CommonResponse<List<ScheduleResponse>> response = CommonResponse
                .<List<ScheduleResponse>>builder()
                .message("Schedule fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(schedules)
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
