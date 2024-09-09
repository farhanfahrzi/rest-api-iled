package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.constant.TraineeStatus;
import com.enigma.Instructor_Led.dto.request.CreateTraineeRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTraineeRequest;
import com.enigma.Instructor_Led.dto.response.TraineeResponse;
import com.enigma.Instructor_Led.entity.ProgrammingLanguage;
import com.enigma.Instructor_Led.entity.Trainee;
import com.enigma.Instructor_Led.repository.ProgrammingLanguageRepository;
import com.enigma.Instructor_Led.repository.TraineeRepository;
import com.enigma.Instructor_Led.service.ProgrammingLanguageService;
import com.enigma.Instructor_Led.service.TraineeService;
import com.enigma.Instructor_Led.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final ProgrammingLanguageService programmingLanguageService;
    private final Validation validation;

    @Override
    public Trainee createTrainee(Trainee trainee) {
        return traineeRepository.saveAndFlush(trainee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TraineeResponse create(CreateTraineeRequest createTraineeRequest) {
        validation.validate(createTraineeRequest);

        ProgrammingLanguage programmingLanguage = programmingLanguageService.getOneById(createTraineeRequest.getProgrammingLanguageId());
        Trainee trainee = Trainee.builder()
                .name(createTraineeRequest.getName())
                .nik(createTraineeRequest.getNik())
                .birthDate(createTraineeRequest.getBirthDate())
                .address(createTraineeRequest.getAddress())
                .email(createTraineeRequest.getEmail())
                .phoneNumber(createTraineeRequest.getPhoneNumber())
                .programmingLanguage(programmingLanguage)
                .status(TraineeStatus.ACTIVE)
                .build();

        Trainee savedTrainee = traineeRepository.save(trainee);
        return mapToResponse(savedTrainee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TraineeResponse update(UpdateTraineeRequest updateTraineeRequest) {
        validation.validate(updateTraineeRequest);

//        Optional<Trainee> traineeOpt = traineeRepository.findById(updateTraineeRequest.getId());
//        if (traineeOpt.isEmpty()) {
//            return null; // Or throw an exception
//        }

        Trainee trainee = getOneById(updateTraineeRequest.getId());
        trainee.setName(updateTraineeRequest.getName());
        trainee.setNik(updateTraineeRequest.getNik());
        trainee.setBirthDate(updateTraineeRequest.getBirthDate());
        trainee.setAddress(updateTraineeRequest.getAddress());
        trainee.setEmail(updateTraineeRequest.getEmail());
        trainee.setPhoneNumber(updateTraineeRequest.getPhoneNumber());
        Trainee updatedTrainee = traineeRepository.save(trainee);

        return mapToResponse(updatedTrainee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TraineeResponse getById(String id) {
        Optional<Trainee> traineeOpt = traineeRepository.findById(id);
        return traineeOpt.map(this::mapToResponse).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Trainee getOneById(String id) {
        return traineeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainee not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TraineeResponse> getAll(Pageable pageable, String name, String email, String nik) {
        Specification<Trainee> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (nik != null && !nik.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("nik"), nik));
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }

        Page<Trainee> trainees = traineeRepository.findAll(spec, pageable);
        return trainees.map(this::mapToResponse);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Trainee trainee = getOneById(id);
        traineeRepository.delete(trainee);
    }

    @Transactional(readOnly = true)
    @Override
    public Trainee getByUserAccountId(String id) {
        return traineeRepository.findByUserAccountId(id);
    }

    private TraineeResponse mapToResponse(Trainee trainee) {
        return TraineeResponse.builder()
                .id(trainee.getId())
                .name(trainee.getName())
                .nik(trainee.getNik())
                .birthDate(trainee.getBirthDate())
                .address(trainee.getAddress())
                .email(trainee.getEmail())
                .phoneNumber(trainee.getPhoneNumber())
                .programmingLanguage(trainee.getProgrammingLanguage().getProgrammingLanguage())
                .status(trainee.getStatus())
                .build();
    }
}
