package com.enigma.Instructor_Led.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAnswerRequest {
    @NotBlank(message = "Question Id is required")
    private String questionId;

    @NotBlank(message = "Answer is required")
    private String answer;

}
