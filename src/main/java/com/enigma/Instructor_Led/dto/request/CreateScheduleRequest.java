package com.enigma.Instructor_Led.dto.request;


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
    private Date date;

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotNull(message = "Trainer is required")
    private String trainerId;
}
