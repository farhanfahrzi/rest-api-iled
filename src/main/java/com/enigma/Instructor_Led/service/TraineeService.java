package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateTraineeRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTraineeRequest;
import com.enigma.Instructor_Led.dto.response.TraineeResponse;
import com.enigma.Instructor_Led.entity.Trainee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TraineeService {
    Trainee createTrainee(Trainee trainee);
    TraineeResponse create(CreateTraineeRequest createTraineeRequest);
    TraineeResponse update(UpdateTraineeRequest updateTraineeRequest);
    TraineeResponse getById(String id);
    Trainee getOneById(String id);
    Page<TraineeResponse> getAll(Pageable pageable, String name, String email, String nik);
    void delete(String id);

    Trainee getByUserAccountId(String id);
}
