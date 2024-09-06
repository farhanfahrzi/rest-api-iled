package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateTrainerRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTrainerRequest;
import com.enigma.Instructor_Led.dto.response.TrainerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainerService {
    TrainerResponse create(CreateTrainerRequest createTraineeRequest);
    TrainerResponse update(UpdateTrainerRequest updateTraineeRequest);
    TrainerResponse getById(String id);
    Page<TrainerResponse> getAll(Pageable pageable);
    void delete(String id);
}
