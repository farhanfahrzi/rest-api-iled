package com.enigma.Instructor_Led.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateScheduleRequest {

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotBlank(message = "Trainer is required")
    private String trainerId;

    @NotBlank(message = "Language is required")
    private String programmingLanguageId;
}
