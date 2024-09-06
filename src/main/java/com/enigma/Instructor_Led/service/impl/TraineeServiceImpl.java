package com.enigma.Instructor_Led.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.enigma.Instructor_Led.dto.request.CreateTraineeRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTraineeRequest;
import com.enigma.Instructor_Led.dto.response.TraineeResponse;
import com.enigma.Instructor_Led.entity.Trainee;
import com.enigma.Instructor_Led.repository.TraineeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeRepository traineeRepository;

    @Override
    public TraineeResponse create(CreateTraineeRequest createTraineeRequest) {
        Trainee trainee = new Trainee();
        trainee.setId(createTraineeRequest.getId());
        trainee.setName(createTraineeRequest.getName());
        // Set other fields as necessary
        trainee = traineeRepository.save(trainee);
        return convertToResponse(trainee);
    }

    @Override
    public TraineeResponse update(UpdateTraineeRequest updateTraineeRequest) {
        Trainee trainee = traineeRepository.findById(updateTraineeRequest.getId())
                .orElseThrow(() -> new RuntimeException("Trainee not found"));
        trainee.setName(updateTraineeRequest.getName());
        // Update other fields as necessary
        trainee = traineeRepository.save(trainee);
        return convertToResponse(trainee);
    }

    @Override
    public TraineeResponse getById(String id) {
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));
        return convertToResponse(trainee);
    }

    @Override
    public List<TraineeResponse> getAll() {
        List<Trainee> trainees = traineeRepository.findAll();
        return trainees.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));
        traineeRepository.delete(trainee);
    }

    private TraineeResponse convertToResponse(Trainee trainee) {
        TraineeResponse response = new TraineeResponse();
        response.setId(trainee.getId());
        response.setName(trainee.getName());
        // Set other fields as necessary
        return response;
    }
}
