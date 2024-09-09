package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateTrainerRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTrainerRequest;
import com.enigma.Instructor_Led.dto.response.TrainerResponse;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.PagingResponse;
import com.enigma.Instructor_Led.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(PathUrl.TRAINER)
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse<TrainerResponse>> createTrainer(@RequestBody CreateTrainerRequest createTrainerRequest) {
        TrainerResponse trainerResponse = trainerService.create(createTrainerRequest);
        CommonResponse<TrainerResponse> response = CommonResponse.<TrainerResponse>builder()
                .message("Trainer created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(trainerResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    @PutMapping
    public ResponseEntity<CommonResponse<TrainerResponse>> updateTrainer(@RequestBody UpdateTrainerRequest updateTrainerRequest) {
        TrainerResponse trainerResponse = trainerService.update(updateTrainerRequest);
        CommonResponse<TrainerResponse> response = CommonResponse.<TrainerResponse>builder()
                .message("Trainer updated successfully")
                .statusCode(HttpStatus.OK.value())
                .data(trainerResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TrainerResponse>> getTrainerById(@PathVariable String id) {
        TrainerResponse trainerResponse = trainerService.getById(id);
        CommonResponse<TrainerResponse> response = CommonResponse.<TrainerResponse>builder()
                .message("Trainer fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(trainerResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<TrainerResponse>>> getAllTrainers(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "email", required = false) String email
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TrainerResponse> trainerPage = trainerService.getAll(pageable,name,email);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(trainerPage.getTotalPages())
                .totalElement(trainerPage.getTotalElements())
                .hasNext(trainerPage.hasNext())
                .hasPrevious(trainerPage.hasPrevious())
                .build();

        CommonResponse<List<TrainerResponse>> response = CommonResponse.<List<TrainerResponse>>builder()
                .message("Successfully retrieved all trainers")
                .statusCode(HttpStatus.OK.value())
                .data(trainerPage.getContent())
                .paging(pagingResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> deleteTrainer(@PathVariable String id) {
        trainerService.delete(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("Trainer deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}