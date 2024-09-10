package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateAnswerRequest;
import com.enigma.Instructor_Led.dto.request.CreateQuestionRequest;
import com.enigma.Instructor_Led.dto.response.QuestionResponse;

import java.util.List;

public interface QuestionService {
    QuestionResponse create(CreateQuestionRequest createQuestionRequest);
    QuestionResponse answerQuestions(CreateAnswerRequest answerRequest);
    QuestionResponse getById(String id);
//    List<QuestionResponse> getAll();
    List<QuestionResponse> getAllByTraineeId();
    List<QuestionResponse> getAllByTrainerId();
    void delete(String id);
}
