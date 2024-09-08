package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateProgrammingLanguageRequest;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.PagingResponse;
import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;
import com.enigma.Instructor_Led.service.ProgrammingLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathUrl.PROGRAMMING_LANGUAGE)
@RequiredArgsConstructor
public class ProgrammingLanguageController {

    private final ProgrammingLanguageService programmingLanguageService;

    @PostMapping
    public ResponseEntity<CommonResponse<ProgrammingLanguageResponse>> createProgrammingLanguage(
            @RequestBody CreateProgrammingLanguageRequest createRequest) {
        ProgrammingLanguageResponse programmingLanguageResponse = programmingLanguageService.create(createRequest);
        CommonResponse<ProgrammingLanguageResponse> response = CommonResponse.<ProgrammingLanguageResponse>builder()
                .message("Programming Language created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(programmingLanguageResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ProgrammingLanguageResponse>> getProgrammingLanguageById(@PathVariable String id) {
       ProgrammingLanguageResponse programmingLanguageResponse = programmingLanguageService.getById(id);
        CommonResponse<ProgrammingLanguageResponse> response = CommonResponse.<ProgrammingLanguageResponse>builder()
                .message("Programming Language get by id successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(programmingLanguageResponse)
                .build();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProgrammingLanguageResponse>>> getAllProgrammingLanguages(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        // Assuming the service method `getAll` returns a paginated result
        Page<ProgrammingLanguageResponse> programmingLanguageResponses = programmingLanguageService.getAll(page, size);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(programmingLanguageResponses.getTotalPages())
                .totalElement(programmingLanguageResponses.getTotalElements())
                .hasNext(programmingLanguageResponses.hasNext())
                .hasPrevious(programmingLanguageResponses.hasPrevious())
                .build();

        CommonResponse<List<ProgrammingLanguageResponse>> response = CommonResponse.<List<ProgrammingLanguageResponse>>builder()
                .message("Successfully retrieved all programming languages")
                .statusCode(HttpStatus.OK.value())
                .data(programmingLanguageResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> deleteProgrammingLanguage(@PathVariable String id) {
        programmingLanguageService.delete(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("Programming Language deleted successfully")
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
