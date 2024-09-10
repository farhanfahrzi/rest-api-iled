package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.UploadImagesRequest;
import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.dto.response.ImageUploadResponse;
import com.enigma.Instructor_Led.entity.DocumentationImage;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.repository.DocumentationImageRepository;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import com.enigma.Instructor_Led.service.DocumentationImageService;
import com.enigma.Instructor_Led.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class DocumentationImageServiceImpl implements DocumentationImageService {

   // private final ScheduleRepository scheduleRepository;
    private final DocumentationImageRepository documentationImageRepository;
    private final ScheduleService scheduleService;
    private final String privateKey;
    private final String uploadEndpoint;
    private final WebClient webClient;

    public DocumentationImageServiceImpl(DocumentationImageRepository documentationImageRepository,
                                         ScheduleService scheduleService,
                                         @Value("${PrivateKey}") String privateKey,
                                         @Value("${imagekit.url.endpoint}")String uploadEndpoint,
                                         WebClient webClient) {
        this.documentationImageRepository = documentationImageRepository;
        this.scheduleService = scheduleService;
        this.privateKey = privateKey;
        this.uploadEndpoint = uploadEndpoint;
        this.webClient = webClient;
    }




//    @Value("${imagekit.private.key}")
//    private String privateKey;
//
//    @Value("${imagekit.url.endpoint}")
//    private String uploadEndpoint;



    @Transactional(readOnly = true)
    @Override
    public DocumentationImage getById(String id) {
        return documentationImageRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Documentation not found")
        );
    }

    @Transactional
    @Override
    public List<DocumentationImageResponse> uploadImages(UploadImagesRequest request) throws IOException {
        Schedule schedule = scheduleService.getById(request.getScheduleId());

        List<DocumentationImage> uploadedImages = new ArrayList<>();
        for (MultipartFile file : request.getImages()) {
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
            String encodedKey = Base64.getEncoder().encodeToString((privateKey + ":").getBytes());

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("file", base64Image);
            body.add("fileName", file.getOriginalFilename());

            ImageUploadResponse response = webClient.post()
                    .uri(uploadEndpoint)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedKey)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(body))
                    .retrieve()
                    .bodyToMono(ImageUploadResponse.class)
                    .block();

            DocumentationImage image = new DocumentationImage();
            image.setLink(response.getUrl());
            image.setSchedule(schedule);
            documentationImageRepository.save(image);

            uploadedImages.add(image);
        }

        // Convert to DocumentationImageResponse and create a response
        List<DocumentationImageResponse> responseList = uploadedImages.stream()
                .map(this::convertToDocumentationImageResponse)
                .collect(Collectors.toList());

        return responseList; // Return the list of DocumentationImageResponse
    }

    // Helper method to convert DocumentationImage to DocumentationImageResponse
    private DocumentationImageResponse convertToDocumentationImageResponse(DocumentationImage image) {
        List<String> links = Arrays.asList(image.getLink().split("\\s*,\\s*"));

        return DocumentationImageResponse.builder()
                .id(image.getId())  // Assuming DocumentationImage has an 'id' field
                .links(links)  // Set the list of links (URLs)
                .scheduleId(image.getSchedule().getId())  // Assuming DocumentationImageResponse needs 'scheduleId'
                .build();
    }
}
