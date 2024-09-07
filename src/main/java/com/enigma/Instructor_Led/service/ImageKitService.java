package com.enigma.Instructor_Led.service;

import okhttp3.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ImageKitService {
    public String uploadFileToImageKit(MultipartFile file);
}
