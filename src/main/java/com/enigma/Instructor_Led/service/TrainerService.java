package com.enigma.Instructor_Led.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.enigma.Instructor_Led.dto.request.CreateTrainerRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTrainerRequest;
import com.enigma.Instructor_Led.dto.response.TrainerResponse;
import com.enigma.Instructor_Led.entity.Trainer;
import com.enigma.Instructor_Led.repository.TrainerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    public TrainerResponse create(CreateTrainerRequest createTrainerRequest) {
        Trainer trainer = new Trainer();
        trainer.setId(createTrainerRequest.getId());
        trainer.setName(createTrainerRequest.getName());
        // Set other fields as necessary
        trainer = trainerRepository.save(trainer);
        return convertToResponse(trainer);
    }

    @Override
    public TrainerResponse update(UpdateTrainerRequest updateTrainerRequest) {
        Trainer trainer = trainerRepository.findById(updateTrainerRequest.getId())
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        trainer.setName(updateTrainerRequest.getName());
        // Update other fields as necessary
        trainer = trainerRepository.save(trainer);
        return convertToResponse(trainer);
    }

    @Override
    public TrainerResponse getById(String id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        return convertToResponse(trainer);
    }

    @Override
    public List<TrainerResponse> getAll() {
        List<Trainer> trainers = trainerRepository.findAll();
        return trainers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        if (!trainerRepository.existsById(id)) {
            throw new RuntimeException("Trainer not found");
        }
        trainerRepository.deleteById(id);
    }

    private TrainerResponse convertToResponse(Trainer trainer) {
        TrainerResponse response = new TrainerResponse();
        response.setId(trainer.getId());
        response.setName(trainer.getName());
        // Set other fields as necessary
        return response;
    }
}

