package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateTrainerRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTrainerRequest;
import com.enigma.Instructor_Led.dto.response.TrainerResponse;

import java.util.List;

public interface TrainerService {
    TrainerResponse create(CreateTrainerRequest createTraineeRequest);
    TrainerResponse update(UpdateTrainerRequest updateTraineeRequest);
    TrainerResponse getById(String id);
    List<TrainerResponse> getAll();
    void delete(String id);
}
