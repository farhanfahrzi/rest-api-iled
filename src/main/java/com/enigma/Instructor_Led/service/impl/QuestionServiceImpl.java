//package com.enigma.Instructor_Led.service.impl;
//
//import com.enigma.Instructor_Led.dto.request.CreateAnswerRequest;
//import com.enigma.Instructor_Led.dto.request.CreateQuestionRequest;
//import com.enigma.Instructor_Led.dto.response.QuestionResponse;
//import com.enigma.Instructor_Led.entity.Question;
//import com.enigma.Instructor_Led.repository.QuestionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class QuestionServiceImpl implements QuestionService {
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Override
//    public QuestionResponse create(CreateQuestionRequest createQuestionRequest) {
//        Question question = Question.builder()
//                .content(createQuestionRequest.getContent())
//                .build();
//        question = questionRepository.save(question);
//        return new QuestionResponse(question.getId(), question.getContent(), question.getAnswer());
//    }
//
//    @Override
//    public QuestionResponse answerQuestions(CreateAnswerRequest answerRequest) {
//        Question question = questionRepository.findById(answerRequest.getQuestionId())
//                .orElseThrow(() -> new RuntimeException("Question not found with id: " + answerRequest.getQuestionId()));
//        question.setAnswer(answerRequest.getAnswer());
//        question = questionRepository.save(question);
//        return new QuestionResponse(question.getId(), question.getContent(), question.getAnswer());
//    }
//
//    @Override
//    public QuestionResponse getById(String id) {
//        Question question = questionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
//        return new QuestionResponse(question.getId(), question.getContent(), question.getAnswer());
//    }
//
//    @Override
//    public List<QuestionResponse> getAll() {
//        return questionRepository.findAll().stream()
//                .map(question -> new QuestionResponse(question.getId(), question.getContent(), question.getAnswer()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void delete(String id) {
//        Question question = questionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
//        questionRepository.delete(question);
//    }
//}