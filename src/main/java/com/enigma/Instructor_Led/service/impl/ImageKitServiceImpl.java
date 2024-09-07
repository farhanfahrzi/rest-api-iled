package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.entity.DocumentationImage;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.repository.DocumentationImageRepository;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import com.enigma.Instructor_Led.service.ImageKitService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageKitServiceImpl implements ImageKitService {
    private final ScheduleRepository scheduleRepository;
    private final DocumentationImageRepository documentationImageRepository;
    private final RestClient restClient;
    private final RestTemplate restTemplate;
    private final String PRIVATE_KEY;
    private final String PUBLIC_KEY;
    private final String URL_ENDPOINT;

    @Autowired
    public ImageKitServiceImpl(
            ScheduleRepository scheduleRepository, DocumentationImageRepository documentationImageRepository, RestClient restClient, RestTemplate restTemplate,
            @Value("${PrivateKey}") String privateKey,
            @Value("${PublicKey}") String publicKey,
            @Value("${UrlEndpoint}") String urlEndpoint
    ) {
        this.scheduleRepository = scheduleRepository;
        this.documentationImageRepository = documentationImageRepository;
        this.restClient = restClient;
        this.restTemplate = restTemplate;
        this.PRIVATE_KEY = privateKey;
        this.PUBLIC_KEY = publicKey;
        this.URL_ENDPOINT = urlEndpoint;
    }

    @PostConstruct
    public void init() {
        try {
            Configuration config = new Configuration(
                    PRIVATE_KEY,
                    PUBLIC_KEY,
                    URL_ENDPOINT);
            ImageKit.getInstance().setConfig(config);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing ImageKit configuration: " + e.getMessage(), e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DocumentationImageResponse uploadImage(MultipartFile file, String scheduleId) throws IOException {
        // Convert file to Base64
        // String encodedFile = Base64.getEncoder().encodeToString(file.getBytes());

        // Create request body
        // Map<String, String> requestBody = new HashMap<>();
        // requestBody.put("file", encodedFile);
        // requestBody.put("fileName", file.getOriginalFilename());

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((PRIVATE_KEY + ":").getBytes()));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create MultipartBody
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        InputStreamResource fileResource = new InputStreamResource(file.getInputStream()) {
            @Override
            public long contentLength() {
                return file.getSize();
            }

            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        body.add("file", fileResource);
        body.add("fileName", file.getOriginalFilename());

        // Create HttpEntity with headers and body
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send request to ImageKit
        ResponseEntity<Map<String, Object>> response;
        try {
            response = restTemplate.exchange(URL_ENDPOINT,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {});
//            response = restClient.post()
//                    .uri(URL_ENDPOINT + "/api/v1/upload")
//                    .body(requestEntity)
////                    .header(HttpHeaders.AUTHORIZATION,
////                            "Basic " + Base64.getEncoder().encodeToString(PUBLIC_KEY.getBytes()),
////                            HttpHeaders.CONTENT_TYPE, "application/json")
//                    .retrieve()
//                    .toEntity(new ParameterizedTypeReference<Map<String, String>>() {});
        } catch (HttpClientErrorException e) {
            System.out.println("Error response body: " + e.getResponseBodyAsString());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        // Extract result from response
        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null || responseBody.get("url") == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get image URL");
        }

        // Find Schedule by ID
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        // Save DocumentationImage to database
        DocumentationImage documentationImage = new DocumentationImage();
        documentationImage.setLink((String) responseBody.get("url")); // URL result from ImageKit
        documentationImage.setSchedule(schedule);
        documentationImage = documentationImageRepository.save(documentationImage);

        // Return response
        return DocumentationImageResponse.builder()
                .id(documentationImage.getId())
                .link(documentationImage.getLink())
                .build();
    }
}
