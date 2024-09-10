package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.dto.request.CreateAnswerRequest;
import com.enigma.Instructor_Led.dto.request.CreateQuestionRequest;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.QuestionResponse;
import com.enigma.Instructor_Led.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/questions")
public class QuestionController {
    private final QuestionService questionService;

    @PreAuthorize("hasRole('ROLE_TRAINEE')")
    @PostMapping
    public ResponseEntity<CommonResponse<QuestionResponse>> createQuestion(@RequestBody CreateQuestionRequest request) {
        QuestionResponse question = questionService.create(request);
        CommonResponse<QuestionResponse> commonResponse = CommonResponse.<QuestionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Question created successfully")
                .data(question)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_TRAINER', 'ROLE_ADMIN')")
    @PostMapping("/answer")
    public ResponseEntity<CommonResponse <QuestionResponse>> answerQuestion(@RequestBody CreateAnswerRequest request) {
        QuestionResponse response = questionService.answerQuestions(request);
        CommonResponse<QuestionResponse> commonResponse = CommonResponse.<QuestionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Question answered successfully")
                .data(response)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_TRAINER', 'ROLE_TRAINEE')")
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse <QuestionResponse>> getQuestionById(@PathVariable String id) {
        QuestionResponse response = questionService.getById(id);
        CommonResponse<QuestionResponse> commonResponse = CommonResponse.<QuestionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Question answered successfully")
                .data(response)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @PreAuthorize("hasRole('ROLE_TRAINEE')")
    @GetMapping(path = "/trainee")
    public ResponseEntity<CommonResponse<List<QuestionResponse>>> getAllQuestionsByTraineeId() {
        List<QuestionResponse> questions = questionService.getAllByTraineeId();
        CommonResponse<List<QuestionResponse>> responses = CommonResponse.<List<QuestionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Questions get all successfuly")
                .data(questions)
                .build();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @GetMapping(path = "/trainer")
    public ResponseEntity<CommonResponse<List<QuestionResponse>>> getAllQuestionsByTrainerId() {
        List<QuestionResponse> questions = questionService.getAllByTrainerId();
        CommonResponse<List<QuestionResponse>> responses = CommonResponse.<List<QuestionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Questions get all successfuly")
                .data(questions)
                .build();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteQuestion(@PathVariable String id) {
        questionService.delete(id);
        CommonResponse<Void> response = CommonResponse.<Void>builder()
                .message("Question deleted sucessfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        ;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
