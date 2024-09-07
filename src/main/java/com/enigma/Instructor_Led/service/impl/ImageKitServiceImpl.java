package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.service.ImageKitService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageKitServiceImpl implements ImageKitService {
    @Override
    public String uploadFileToImageKit(MultipartFile file) {
        try {
            // Konversi MultipartFile ke byte array
            byte[] fileBytes = file.getBytes();

            // Membuat request untuk upload file ke ImageKit
            FileCreateRequest request = new FileCreateRequest(fileBytes, file.getOriginalFilename());

            // Lakukan upload ke ImageKit
            Result result = ImageKit.getInstance().upload(request);

            // Jika upload berhasil, kembalikan URL gambar
            if (result != null) {
                return result.getUrl();
            } else {
                throw new RuntimeException("Image upload failed");
            }
        } catch (IOException | InternalServerException | BadRequestException | UnknownException | ForbiddenException |
                 TooManyRequestsException | UnauthorizedException e) {
            throw new RuntimeException("Error uploading file to ImageKit: " + e.getMessage());
        }
    }
}
