package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.UpdateDocumentationImageRequest;
import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;
import com.enigma.Instructor_Led.dto.response.TrainerResponse;
import com.enigma.Instructor_Led.entity.*;
import com.enigma.Instructor_Led.repository.*;
import com.enigma.Instructor_Led.service.ImageKitService;
import com.enigma.Instructor_Led.service.ProgrammingLanguageService;
import com.enigma.Instructor_Led.service.ScheduleService;
import com.enigma.Instructor_Led.service.TrainerService;
import com.enigma.Instructor_Led.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TrainerRepository trainerRepository;
    private final ProgrammingLanguageRepository programmingLanguageRepository;
    private final DocumentationImageRepository documentationImageRepository;

    private final ImageKitService imageKitService;
    private final Validation validation;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ScheduleResponse create(CreateScheduleRequest request) {
        // Validation
        validation.validate(request);

        // Find trainer from request
        Trainer trainer = trainerRepository.findById(request.getTrainerId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found")
        );

        // Find programming language from request
        ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(request.getProgrammingLanguageId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Programming Language not found")
        );

        // Create schedule from request
        Schedule schedule = Schedule.builder()
                .date(request.getDate())
                .topic(request.getTopic())
                .trainer(trainer)
                .programmingLanguage(programmingLanguage)
                .build();
        scheduleRepository.saveAndFlush(schedule);

        // Update programming language trainer
        programmingLanguage.setTrainer(trainer);

        // Create response
        return convertToResponse(schedule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Schedule createDemo(CreateScheduleRequest createScheduleRequest) {
        validation.validate(createScheduleRequest);
        // Cari Trainer berdasarkan trainerId
        Trainer trainer = trainerRepository.findById(createScheduleRequest.getTrainerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found"));
        Schedule schedule = Schedule.builder()
                .date(createScheduleRequest.getDate())
                .topic(createScheduleRequest.getTopic())
                .trainer(trainer)
                .build();
        // Simpan Schedule ke repository
        schedule = scheduleRepository.saveAndFlush(schedule);

        // Mapping response
        ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                .id(schedule.getId())
                .date(schedule.getDate())
                .topic(schedule.getTopic())
                .trainerId(schedule.getTrainer().getId())  // Dapatkan trainerId dari objek trainer
                .build();

        return schedule;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ScheduleResponse update(UpdateScheduleRequest request) {
        // Validation
        validation.validate(request);

        // Find schedule from request
        Schedule schedule = scheduleRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Update existing schedule
        Schedule updatedSchedule = scheduleRepository.saveAndFlush(schedule);

        // Create response
        return convertToResponse(updatedSchedule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DocumentationImageResponse updateDocumentation(MultipartFile file, UpdateDocumentationImageRequest request) {
//        System.out.println("id : " + request.getScheduleId());
//        System.out.println("name : " + file.getName());
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("File is required");
//        }
//
//        // Cari schedule berdasarkan scheduleId
//        Schedule schedule = getOneById(request.getScheduleId());
//        System.out.println("schedule id : " + schedule.getId());
//
//        // Upload file ke ImageKit
//        String imageUrl = imageKitService.uploadFileToImageKit(file);
//        System.out.println("image url : " + imageUrl);
//
//        // Simpan DocumentationImage baru
//        DocumentationImage documentationImage = DocumentationImage.builder()
//                .link(imageUrl)
//                .schedule(schedule)
//                .build();
//        documentationImageRepository.save(documentationImage);
//        System.out.println("documentation id : " + documentationImage.getId());
//
//        // Return response
//        return new DocumentationImageResponse(documentationImage.getId(), documentationImage.getLink());
        return null;
    }

    @Override
    public ScheduleResponse getById(String id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return convertToResponse(schedule);
    }

    @Transactional(readOnly = true)
    @Override
    public Schedule getOneById(String id) {
//        try {
//            return scheduleRepository.findByScheduleId(id);
//        } catch (RuntimeException e) {
//            throw new RuntimeException("Schedule not found", e);
//        }
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        System.out.println(schedule.isPresent());
        return schedule.orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleResponse> getAll(String language, String startDate, String endDate) {
        Date start = startDate != null && !startDate.isEmpty() ? Date.valueOf(startDate) : null;
        Date end = endDate != null && !endDate.isEmpty() ? Date.valueOf(endDate) : null;

        if (language != null && !language.isEmpty() && startDate != null) {
            List<Schedule> schedules;
            if (endDate != null) {
                schedules = scheduleRepository.findSchedulesByProgrammingLanguageNameAndDateBetween(start, end, "%" + language + "%");
            } else {
                schedules = scheduleRepository.findSchedulesByProgrammingLanguageNameAndDateGreaterThanEqual(start, "%" + language + "%");
            }
            return schedules.stream()
                    .map(this::convertToResponse)
                    .toList();
        }

        if (language != null && !language.isEmpty() && endDate != null) {
            List<Schedule> schedules = scheduleRepository.findSchedulesByProgrammingLanguageNameAndDateLessThanEqual(end, "%" + language + "%");
            return schedules.stream()
                    .map(this::convertToResponse)
                    .toList();
        }

        if (language != null && !language.isEmpty()) {
            List<Schedule> schedules = scheduleRepository.findSchedulesByProgrammingLanguageName("%" + language + "%");
            return schedules.stream()
                    .map(this::convertToResponse)
                    .toList();
        }

        if (startDate != null && endDate != null) {
            List<Schedule> schedules = scheduleRepository.findSchedulesByDateBetween(start, end);
            return schedules.stream()
                    .map(this::convertToResponse)
                    .toList();
        }

        if (startDate != null && !startDate.isEmpty()) {
            List<Schedule> schedules = scheduleRepository.findSchedulesByDateGreaterThanEqual(start);
            return schedules.stream()
                    .map(this::convertToResponse)
                    .toList();
        }

        if (endDate != null && !endDate.isEmpty()) {
            List<Schedule> schedules = scheduleRepository.findSchedulesByDateLessThanEqual(end);
            return schedules.stream()
                    .map(this::convertToResponse)
                    .toList();
        }

        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleResponse> getAllByTraineeId(String id) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleResponse> getAllByTrainerId(String id) {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found")
        );
        List<Schedule> schedules = scheduleRepository.findAllByTrainerId(trainer.getId());
        return schedules.stream().map(this::convertToResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }


    private ScheduleResponse convertToResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .date(schedule.getDate())
                .topic(schedule.getTopic())
                .trainerId(schedule.getTrainer().getId())
                .programmingLanguageId(schedule.getProgrammingLanguage().getId())
                .documentationImages(null)
                .build();
    }
}
