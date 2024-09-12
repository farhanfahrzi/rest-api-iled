package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.UploadImagesRequest;
import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.entity.DocumentationImage;

import java.io.IOException;
import java.util.List;

public interface DocumentationImageService {
    DocumentationImage getById(String id);
    List<DocumentationImageResponse> uploadImages(UploadImagesRequest request) throws IOException;
}
