package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.UpdateDocumentationImageRequest;
import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import okhttp3.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageKitService {
    DocumentationImageResponse uploadImage(MultipartFile file, String scheduleId) throws IOException;
    DocumentationImageResponse uploadDocs(MultipartFile file) throws IOException;
}
