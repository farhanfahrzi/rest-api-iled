package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.entity.DocumentationImage;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.repository.DocumentationImageRepository;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import com.enigma.Instructor_Led.service.ImageKitService;
import com.enigma.Instructor_Led.service.ScheduleService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
import java.util.Map;

@Service
public class ImageKitServiceImpl implements ImageKitService {
    private final DocumentationImageRepository documentationImageRepository;
    private final ScheduleService scheduleService;
    private final RestTemplate restTemplate;
    private final String PRIVATE_KEY;
    private final String PUBLIC_KEY;
    private final String URL_ENDPOINT;

    @Autowired
    public ImageKitServiceImpl(
            DocumentationImageRepository documentationImageRepository,
            ScheduleService scheduleService,
            RestTemplate restTemplate,
            @Value("${PrivateKey}") String privateKey,
            @Value("${PublicKey}") String publicKey,
            @Value("${UrlEndpoint}") String urlEndpoint
    ) {
        this.documentationImageRepository = documentationImageRepository;
        this.scheduleService = scheduleService;
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
    public DocumentationImageResponse uploadImage(MultipartFile file, String id) throws IOException {
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
        } catch (HttpClientErrorException e) {
            System.out.println("Error response body: " + e.getResponseBodyAsString());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        // Extract result from response
        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null || responseBody.get("url") == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get image URL");
        }
        System.out.println(responseBody.get("url"));

        // Find Schedule by ID
        System.out.println(id);
        Schedule schedule = scheduleService.getById(id);
//        if(optionalSchedule.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule Not Found");
//        }

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
