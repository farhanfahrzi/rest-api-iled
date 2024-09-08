package com.enigma.Instructor_Led.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAttendanceDetailRequest {

    @NotNull(message = "TraineeId is required")
    private String traineeId;
}
