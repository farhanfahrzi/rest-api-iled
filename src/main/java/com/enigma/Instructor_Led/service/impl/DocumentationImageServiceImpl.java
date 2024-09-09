package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.entity.DocumentationImage;
import com.enigma.Instructor_Led.repository.DocumentationImageRepository;
import com.enigma.Instructor_Led.service.DocumentationImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DocumentationImageServiceImpl implements DocumentationImageService {
    private final DocumentationImageRepository documentationImageRepository;

    @Transactional(readOnly = true)
    @Override
    public DocumentationImage getById(String id) {
        return documentationImageRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Documentation not found")
        );
    }
}
