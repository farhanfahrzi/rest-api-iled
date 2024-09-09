package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.UpdateDocumentationImageRequest;
import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.entity.*;
import com.enigma.Instructor_Led.repository.*;
import com.enigma.Instructor_Led.service.*;
import com.enigma.Instructor_Led.specification.ScheduleSpecification;
import com.enigma.Instructor_Led.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final ProgrammingLanguageService programmingLanguageService;
    private final DocumentationImageService documentationImageService;
    private final UserService userService;

    private final Validation validation;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ScheduleResponse create(CreateScheduleRequest request) {
        // Validation
        validation.validate(request);

        // Find trainer from request
        Trainer trainer = trainerService.getOneById(request.getTrainerId());

        // Find programming language from request
        ProgrammingLanguage programmingLanguage = programmingLanguageService.getOneById(request.getProgrammingLanguageId());

        // Checking if the trainer actually have corresponding programming language
        if (!trainer.getProgrammingLanguages().contains(programmingLanguage)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer ID and Programming Language ID doesn't match");
        }

        // Create schedule from request
        Schedule schedule = Schedule.builder()
                .date(request.getDate())
                .topic(request.getTopic())
                .trainer(trainer)
                .programmingLanguage(programmingLanguage)
                .documentationImages(new ArrayList<>())
                .questions(new ArrayList<>())
                .build();
        scheduleRepository.saveAndFlush(schedule);

        // Update programming language trainer
//        programmingLanguage.setTrainer(trainer);

        // Create response
        return convertToResponse(schedule);
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
    public ScheduleResponse updateDocumentation(UpdateDocumentationImageRequest request) {
        // Validation
        validation.validate(request);

        // Find schedule
        Schedule schedule = scheduleRepository.findById(request.getScheduleId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        // Set schedule id for documentation image
        List<DocumentationImage> documentationImages = request.getId().stream().map(
                id -> {
                    DocumentationImage documentationImage = documentationImageService.getById(id);
                    documentationImage.setSchedule(schedule);
                    return documentationImage;
                }
        ).toList();

        // Update documentation image for schedule
        schedule.getDocumentationImages().addAll(documentationImages);
        Schedule updatedSchedule = scheduleRepository.saveAndFlush(schedule);

        // Create response
        return convertToResponse(updatedSchedule);
    }

    @Transactional(readOnly = true)
    @Override
    public Schedule getById(String id) {
        System.out.println("id inputted: " + id);
        return scheduleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Schedule> getAll(Integer size, Integer page, String language, String startDate, String endDate) {
        Date start = startDate != null && !startDate.isEmpty() ? Date.valueOf(startDate) : null;
        Date end = endDate != null && !endDate.isEmpty() ? Date.valueOf(endDate) : null;

        Pageable pageable = PageRequest.of(page, size);
        Specification<Schedule> specification = Specification.where(null);

        if (language != null && !language.isEmpty() && startDate != null) {
            if (endDate != null) {
                specification = specification.and(ScheduleSpecification.hasLanguageAndStartDateAndEndDate(language,
                        start, end));
            } else {
                specification = specification.and(ScheduleSpecification.hasLanguageAndStartDate(language, start));
            }
            return scheduleRepository.findAll(specification, pageable);
        }

        if (language != null && !language.isEmpty() && endDate != null) {
            specification = specification.and(ScheduleSpecification.hasLanguageAndEndDate(language, end));
            return scheduleRepository.findAll(specification, pageable);
        }

        if (language != null && !language.isEmpty()) {
            specification = specification.and(ScheduleSpecification.hasLanguage(language));
            return scheduleRepository.findAll(specification, pageable);
        }

        if (startDate != null && endDate != null) {
            specification = specification.and(ScheduleSpecification.hasStartDateAndEndDate(start, end));
            return scheduleRepository.findAll(specification, pageable);
        }

        if (startDate != null && !startDate.isEmpty()) {
            specification = specification.and(ScheduleSpecification.hasStartDate(start));
            return scheduleRepository.findAll(specification, pageable);
        }

        if (endDate != null && !endDate.isEmpty()) {
            specification = specification.and(ScheduleSpecification.hasEndDate(end));
            return scheduleRepository.findAll(specification, pageable);
        }

        return scheduleRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ScheduleResponse> getAllByTraineeId(Integer page, Integer size, String id) {
        Trainee trainee = traineeService.getOneById(id);

        UserAccount userAccount = userService.getByContext();
        if (!userAccount.getId().equals(trainee.getUserAccount().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You don't have permission to get schedules for this trainee");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> pagedSchedules = scheduleRepository.findAllByTraineeId(pageable, trainee.getId());
        return pagedSchedules.map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ScheduleResponse> getAllByTrainerId(Integer page, Integer size, String id) {
        Trainer trainer = trainerService.getOneById(id);

        UserAccount userAccount = userService.getByContext();
        if (!userAccount.getId().equals(trainer.getUserAccount().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You don't have permission to get schedules for this trainer");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> pagedSchedules = scheduleRepository.findAllByTrainerId(pageable, trainer.getId());
        return pagedSchedules.map(this::convertToResponse);
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
                .documentationImages(schedule.getDocumentationImages().stream().map(
                        doc -> DocumentationImageResponse.builder()
                                .id(doc.getId())
                                .link(doc.getLink())
                                .build()
                ).toList())
                .build();
    }
}
