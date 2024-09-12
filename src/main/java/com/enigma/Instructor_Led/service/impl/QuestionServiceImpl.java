package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.constant.QuestionStatus;
import com.enigma.Instructor_Led.dto.request.CreateAnswerRequest;
import com.enigma.Instructor_Led.dto.request.CreateQuestionRequest;
import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;
import com.enigma.Instructor_Led.dto.response.QuestionResponse;
import com.enigma.Instructor_Led.dto.response.TraineeResponse;
import com.enigma.Instructor_Led.entity.*;
import com.enigma.Instructor_Led.repository.QuestionRepository;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import com.enigma.Instructor_Led.repository.TraineeRepository;
import com.enigma.Instructor_Led.repository.TrainerRepository;
import com.enigma.Instructor_Led.service.QuestionService;
import com.enigma.Instructor_Led.service.TraineeService;
import com.enigma.Instructor_Led.service.TrainerService;
import com.enigma.Instructor_Led.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ScheduleRepository scheduleRepository;
    private final TraineeService traineeService;
    private final UserService userService;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainerService trainerService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public QuestionResponse create(CreateQuestionRequest createQuestionRequest) {
        Schedule schedule = scheduleRepository.findById(createQuestionRequest.getScheduleId()).orElseThrow(()->new RuntimeException("id Not Found"));
//        Trainee trainee = traineeService.getOneById(createQuestionRequest.getTraineeId());

        UserAccount userAccount = userService.getByContext();
//        if (!userAccount.getId().equals(trainee.getUserAccount().getId())) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
//                    "You don't have permission to get schedules for this trainee");
//        }

        Trainee trainee = traineeRepository.findByUserAccountId(userAccount.getId());

        Question question = Question.builder()
                .question(createQuestionRequest.getQuestion())
                .status(QuestionStatus.UNANSWERED)
                .schedule(schedule)
                .trainee(trainee)
                .build();
        questionRepository.saveAndFlush(question);
        return mapToResponse (question);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public QuestionResponse answerQuestions(CreateAnswerRequest answerRequest) {

        Question question = questionRepository.findById(answerRequest.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + answerRequest.getQuestionId()));

        UserAccount userAccount = userService.getByContext();
        Trainer trainer = trainerRepository.findByUserAccountId(userAccount.getId());

        String trainerId = question.getSchedule().getTrainer().getId();

        if (!trainer.getId().equals(trainerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed");
        }

        question.setAnswer(answerRequest.getAnswer());
        question.setStatus(QuestionStatus.ANSWERED);
        questionRepository.saveAndFlush(question);
        return mapToResponse (question);
    }

    @Transactional(readOnly = true)
    @Override
    public QuestionResponse getById(String id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
        return mapToResponse (question);
    }

//    @Transactional
//    @Override
//    public List<QuestionResponse> getAll() {
//        List<Question> questions = questionRepository.findAll();
//        return questions.stream().map(this::mapToResponse).toList();
//    }

    @Transactional(readOnly = true)
    @Override
    public List<QuestionResponse> getAllByTraineeId() {
        UserAccount userAccount = userService.getByContext();
//        if (!userAccount.getId().equals(trainer.getUserAccount().getId())) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
//                    "You don't have permission to get schedules for this trainer");
//        }

        Trainee trainee = traineeRepository.findByUserAccountId(userAccount.getId());

        List<Question> questions = questionRepository.findByTraineeId(trainee.getId());
        return questions.stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<QuestionResponse> getAllByTrainerId() {
        UserAccount userAccount = userService.getByContext();
//        if (!userAccount.getId().equals(trainer.getUserAccount().getId())) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
//                    "You don't have permission to get schedules for this trainer");
//        }

        Trainer trainer = trainerRepository.findByUserAccountId(userAccount.getId());

        List<Question> questions = questionRepository.findByTrainerId(trainer.getId());
        return questions.stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<QuestionResponse> getAllByStatus(String status) {
        QuestionStatus questionStatus = QuestionStatus.valueOf(status);
        List<Question> questions = questionRepository.findByStatus(questionStatus);
        return questions.stream().map(this::mapToResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
        questionRepository.delete(question);
    }


    private QuestionResponse mapToResponse (Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .answer(question.getAnswer())
                .status(question.getStatus().name())
                .trainee(TraineeResponse
                        .builder()
                        .id(question.getTrainee().getId())
                        .email(question.getTrainee().getEmail())
                        .nik(question.getTrainee().getNik())
                        .phoneNumber(question.getTrainee().getPhoneNumber())
                        .address(question.getTrainee().getAddress())
                        .status(question.getTrainee().getStatus())
                        .birthDate(question.getTrainee().getBirthDate())
                        .programmingLanguage(question.getTrainee().getProgrammingLanguage().getProgrammingLanguage())
                        .username(question.getTrainee().getUserAccount().getUsername())
                        .build())
                .build();
    }
}