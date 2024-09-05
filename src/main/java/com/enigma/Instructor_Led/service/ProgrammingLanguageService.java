package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateProgrammingLanguageRequest;
import com.enigma.Instructor_Led.dto.request.UpdateProgrammingLanguageRequest;
import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;

import java.util.List;

public interface ProgrammingLanguageService {
    ProgrammingLanguageResponse create(CreateProgrammingLanguageRequest createProgrammingLanguageRequest);
    ProgrammingLanguageResponse update(UpdateProgrammingLanguageRequest updateProgrammingLanguageRequest);
    ProgrammingLanguageResponse getById(String id);
    List<ProgrammingLanguageResponse> getAll();
    void delete (String id);
}
