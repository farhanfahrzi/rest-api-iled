package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.constant.UserRole;
import com.enigma.Instructor_Led.dto.request.CreateTrainerRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTrainerRequest;
import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;
import com.enigma.Instructor_Led.dto.response.TrainerResponse;
import com.enigma.Instructor_Led.entity.*;
import com.enigma.Instructor_Led.repository.ProgrammingLanguageRepository;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import com.enigma.Instructor_Led.repository.TrainerRepository;
import com.enigma.Instructor_Led.repository.UserAccountRepository;
import com.enigma.Instructor_Led.service.ProgrammingLanguageService;
import com.enigma.Instructor_Led.service.RoleService;
import com.enigma.Instructor_Led.service.TrainerService;
import com.enigma.Instructor_Led.service.UserService;
import com.enigma.Instructor_Led.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final UserService userService;
    private final ProgrammingLanguageService programmingLanguageService;
    private final Validation validation;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TrainerResponse create(CreateTrainerRequest createTrainerRequest) {
        validation.validate(createTrainerRequest);

        if (userAccountRepository.findByUsername(createTrainerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Encode password
        String hashPassword = passwordEncoder.encode(createTrainerRequest.getPassword());

        Role role = roleService.getOrSave(UserRole.ROLE_TRAINER);
        UserAccount account = UserAccount.builder()
                .username(createTrainerRequest.getUsername())
                .password(hashPassword)
                .isEnable(true)
                .roles(List.of(role))
                .build();

        userAccountRepository.saveAndFlush(account);

        Trainer trainer = Trainer.builder()
                .name(createTrainerRequest.getName())
                .email(createTrainerRequest.getEmail())
                .birthDate(createTrainerRequest.getBirthDate())
                .phoneNumber(createTrainerRequest.getPhoneNumber())
                .address(createTrainerRequest.getAddress())
                .userAccount(account)
                .build();

        List<ProgrammingLanguage> programmingLanguages = createTrainerRequest.getProgrammingLanguages().stream()
                .map(programmingLanguageService::getOneById)
                .toList();

        trainer.setProgrammingLanguages(programmingLanguages);
        Trainer savedTrainer = trainerRepository.save(trainer);

        return mapToResponse(savedTrainer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TrainerResponse update(UpdateTrainerRequest updateTrainerRequest) {
        validation.validate(updateTrainerRequest);

        Trainer trainer = getOneById(updateTrainerRequest.getId());
        UserAccount userByContext = userService.getByContext();
        if (!userByContext.getId().equals(trainer.getUserAccount().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not found");
        }

        if (userAccountRepository.findByUsername(updateTrainerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        String hashPassword = passwordEncoder.encode(updateTrainerRequest.getPassword());
        Role role = roleService.getOrSave(UserRole.ROLE_TRAINER);
        UserAccount account = UserAccount.builder()
                .username(updateTrainerRequest.getUsername())
                .password(hashPassword)
                .isEnable(true)
                .roles(List.of(role))
                .build();

        userAccountRepository.saveAndFlush(account);

        // Update trainer details
        trainer.setName(updateTrainerRequest.getName());
        trainer.setEmail(updateTrainerRequest.getEmail());
        trainer.setBirthDate(updateTrainerRequest.getBirthDate());
        trainer.setPhoneNumber(updateTrainerRequest.getPhoneNumber());
        trainer.setAddress(updateTrainerRequest.getAddress());
        trainer.setUserAccount(account);

        List<ProgrammingLanguage> currentLanguages = trainer.getProgrammingLanguages();
        List<ProgrammingLanguage> newLanguages = updateTrainerRequest.getProgrammingLanguages().stream()
                .map(programmingLanguageService::getOneById)
                .toList();

        currentLanguages.removeIf(existingLanguage ->
                newLanguages.stream().noneMatch(newLanguage -> newLanguage.getId().equals(existingLanguage.getId()))
        );

        newLanguages.stream()
                .filter(newLanguage ->
                        currentLanguages.stream().noneMatch(existingLanguage -> existingLanguage.getId().equals(newLanguage.getId()))
                )
                .forEach(currentLanguages::add);
        trainer.setProgrammingLanguages(currentLanguages);
        Trainer updatedTrainer = trainerRepository.save(trainer);
        return mapToResponse(updatedTrainer);
    }


    @Transactional(readOnly = true)
    @Override
    public TrainerResponse getById(String id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        return mapToResponse(trainer);
    }

    @Override
    public Trainer getOneById(String id) {
      return trainerRepository.findById(id).orElseThrow(() -> new RuntimeException("Trainer not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TrainerResponse> getAll(Pageable pageable, String name, String email) {
        Specification<Trainer> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }

        Page<Trainer> trainees = trainerRepository.findAll(spec, pageable);
        return trainees.map(this::mapToResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Trainer trainer = getOneById(id);
        trainerRepository.deleteById(id);
    }


    private TrainerResponse mapToResponse(Trainer trainer) {
        List<ProgrammingLanguageResponse> programmingLanguageResponses = trainer.getProgrammingLanguages().stream()
                .map(programmingLanguage -> ProgrammingLanguageResponse.builder()
                        .id(programmingLanguage.getId())
                        .programmingLanguage(programmingLanguage.getProgrammingLanguage())
                        .build())
                .collect(Collectors.toList());

        return TrainerResponse.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .email(trainer.getEmail())
                .birthDate(trainer.getBirthDate())
                .phoneNumber(trainer.getPhoneNumber())
                .address(trainer.getAddress())
                .userAccountId(trainer.getUserAccount() != null ? trainer.getUserAccount().getId() : null)
                .programmingLanguages(programmingLanguageResponses)
                .build();
    }

}
