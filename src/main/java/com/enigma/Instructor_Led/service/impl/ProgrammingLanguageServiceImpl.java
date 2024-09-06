package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.CreateProgrammingLanguageRequest;
import com.enigma.Instructor_Led.dto.request.UpdateProgrammingLanguageRequest;
import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;
import com.enigma.Instructor_Led.entity.ProgrammingLanguage;
import com.enigma.Instructor_Led.repository.ProgrammingLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgrammingLanguageServiceImpl implements ProgrammingLanguageService {

    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;

    @Override
    public ProgrammingLanguageResponse create(CreateProgrammingLanguageRequest request) {
        ProgrammingLanguage programmingLanguage = ProgrammingLanguage.builder()
                .programmingLanguage(request.getProgrammingLanguage())
                .build();
        programmingLanguage = programmingLanguageRepository.save(programmingLanguage);
        return new ProgrammingLanguageResponse(programmingLanguage.getId(), programmingLanguage.getProgrammingLanguage());
    }

    @Override
    public ProgrammingLanguageResponse update(UpdateProgrammingLanguageRequest request) {
        ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Programming language not found with id: " + request.getId()));
        programmingLanguage.setProgrammingLanguage(request.getProgrammingLanguage());
        programmingLanguage = programmingLanguageRepository.save(programmingLanguage);
        return new ProgrammingLanguageResponse(programmingLanguage.getId(), programmingLanguage.getProgrammingLanguage());
    }

    @Override
    public ProgrammingLanguageResponse getById(String id) {
        ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Programming language not found with id: " + id));
        return new ProgrammingLanguageResponse(programmingLanguage.getId(), programmingLanguage.getProgrammingLanguage());
    }

    @Override
    public List<ProgrammingLanguageResponse> getAll() {
        return programmingLanguageRepository.findAll().stream()
                .map(pl -> new ProgrammingLanguageResponse(pl.getId(), pl.getProgrammingLanguage()))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Programming language not found with id: " + id));
        programmingLanguageRepository.delete(programmingLanguage);
    }
}