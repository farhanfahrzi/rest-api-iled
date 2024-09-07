package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.CreateTrainerRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTrainerRequest;
import com.enigma.Instructor_Led.dto.response.TrainerResponse;
import com.enigma.Instructor_Led.entity.ProgrammingLanguage;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.entity.Trainer;
import com.enigma.Instructor_Led.repository.ProgrammingLanguageRepository;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import com.enigma.Instructor_Led.repository.TrainerRepository;
import com.enigma.Instructor_Led.service.TrainerService;
import com.enigma.Instructor_Led.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final ProgrammingLanguageRepository programmingLanguageRepository;
    private final Validation validation;
    private final ScheduleRepository scheduleRepository;

    @Override
    public TrainerResponse create(CreateTrainerRequest createTrainerRequest) {
        validation.validate(createTrainerRequest);

        Trainer trainer = Trainer.builder()
                .name(createTrainerRequest.getName())
                .email(createTrainerRequest.getEmail())
                .birthDate(createTrainerRequest.getBirthDate())
                .phoneNumber(createTrainerRequest.getPhoneNumber())
                .address(createTrainerRequest.getAddress())
                .build();

//        List<ProgrammingLanguage> programmingLanguages = createTrainerRequest.getProgrammingLanguages().stream()
//                .map(langId -> programmingLanguageRepository.findById(langId)
//                        .orElseThrow(() -> new RuntimeException("Programming Language not found")))
//                .collect(Collectors.toList());
//
//        trainer.setProgrammingLanguages(programmingLanguages);
        Trainer savedTrainer = trainerRepository.save(trainer);

        return mapToResponse(savedTrainer);
    }

    @Override
    public TrainerResponse update(UpdateTrainerRequest updateTrainerRequest) {
        validation.validate(updateTrainerRequest);

        Trainer trainer = trainerRepository.findById(updateTrainerRequest.getId())
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        trainer.setName(updateTrainerRequest.getName());
        trainer.setEmail(updateTrainerRequest.getEmail());
        trainer.setBirthDate(updateTrainerRequest.getBirthDate());
        trainer.setPhoneNumber(updateTrainerRequest.getPhoneNumber());
        trainer.setAddress(updateTrainerRequest.getAddress());

        // Handle Programming Languages
        List<ProgrammingLanguage> programmingLanguages = updateTrainerRequest.getProgrammingLanguages().stream()
                .map(langId -> programmingLanguageRepository.findById(langId)
                        .orElseThrow(() -> new RuntimeException("Programming Language not found")))
                .collect(Collectors.toList());

        // trainer.setProgrammingLanguages(programmingLanguages);
        Trainer updatedTrainer = trainerRepository.save(trainer);

        return mapToResponse(updatedTrainer);
    }

    @Override
    public TrainerResponse getById(String id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        return mapToResponse(trainer);
    }

    @Override
    public Page<TrainerResponse> getAll(Pageable pageable) {
        Page<Trainer> trainerPage = trainerRepository.findAll(pageable);
        return trainerPage.map(this::mapToResponse);
    }

    @Override
    public void delete(String id) {
        trainerRepository.deleteById(id);
    }


    private TrainerResponse mapToResponse(Trainer trainer) {
//        List<String> programmingLanguages = trainer.getProgrammingLanguages().stream()
//                .map(ProgrammingLanguage::getProgrammingLanguage)
//                .collect(Collectors.toList());

        return TrainerResponse.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .email(trainer.getEmail())
                .birthDate(trainer.getBirthDate())
                .phoneNumber(trainer.getPhoneNumber())
                .address(trainer.getAddress())
                .userAccountId(trainer.getUserAccount() != null ? trainer.getUserAccount().getId() : null)
                // .programmingLanguages(programmingLanguages)
                .build();
    }
}
