package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.entity.Trainer;
import com.enigma.Instructor_Led.repository.TraineeRepository;
import com.enigma.Instructor_Led.repository.TrainerRepository;
import com.enigma.Instructor_Led.service.ScheduleService;
import com.enigma.Instructor_Led.util.Validation;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainerRepository trainerRepository;
    private final Validation validation;

//    @Override
//    public ScheduleResponse create(CreateScheduleRequest createScheduleRequest) {
//        Schedule schedule = new Schedule();
//        schedule.setId(createScheduleRequest.getId());
//        schedule.setName(createScheduleRequest.getName());
//        // Set other fields as necessary
//        schedule = scheduleRepository.save(schedule);
//        return convertToResponse(schedule);
//    }

//    @Override
//    public ScheduleResponse update(UpdateScheduleRequest updateScheduleRequest) {
//        Schedule schedule = scheduleRepository.findById(updateScheduleRequest.getId())
//                .orElseThrow(() -> new RuntimeException("Schedule not found"));
//        schedule.setName(updateScheduleRequest.getName());
//        // Update other fields as necessary
//        schedule = scheduleRepository.save(schedule);
//        return convertToResponse(schedule);
//    }

//    @Override
//    public ScheduleResponse getById(String id) {
//        Schedule schedule = scheduleRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Schedule not found"));
//        return convertToResponse(schedule);
//    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Schedule create(CreateScheduleRequest createScheduleRequest) {
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


    @Override
    public ScheduleResponse update(UpdateScheduleRequest updateScheduleRequest) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Schedule getById(String id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if(optionalSchedule.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule Not Found");
        }
        return optionalSchedule.get();

    }

    @Override
    public ScheduleResponse getOneById(String id) {
        System.out.println("yang beda======================" + id);
        Optional<Schedule> byId = scheduleRepository.findById("90601087-c23d-41a1-86b2-003e21abbcde");
        System.out.println("==============" + byId);
        return null;
//        return scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @Override
    public List<ScheduleResponse> getAll() {
        return List.of();
    }

//    @Override
//    public List<ScheduleResponse> getAll() {
//        List<Schedule> schedules = scheduleRepository.findAll();
//        return schedules.stream()
//                .map(this::convertToResponse)
//                .collect(Collectors.toList());
//    }

    @Override
    public void delete(String id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }

    private ScheduleResponse convertScheduleToScheduleResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .topic(schedule.getTopic())
                .date(schedule.getDate())
                .trainerId(String.valueOf(schedule.getTrainer()))
                .build();
    }
}
