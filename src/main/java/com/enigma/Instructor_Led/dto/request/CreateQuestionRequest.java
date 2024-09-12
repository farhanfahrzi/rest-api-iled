package com.enigma.Instructor_Led.dto.request;

import com.enigma.Instructor_Led.constant.QuestionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuestionRequest {

    @NotBlank(message = "Question is required")
    private String question;

    @NotBlank(message = "Schedule ID is required")
    private String scheduleId;

    public String getScheduleId() {
        return scheduleId;
    }

//    @NotBlank(message = "Trainee ID is required")
//    private String traineeId;

//    @NotNull(message = "Status is required")
//    private QuestionStatus status;

//    public QuestionStatus getStatus() {
//        return status;
//    }
}