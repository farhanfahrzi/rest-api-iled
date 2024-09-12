package com.enigma.Instructor_Led.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateScheduleRequest {
    @NotBlank(message = "Id is required")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    @Pattern(regexp = "^\\\\d{4}-\\d{2}-\\d{2}$", message = "format tanggal harus 'yyyy-MM-dd'")
    private Date date;

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotBlank(message = "Link schedule is required")
    private String link;

    @NotBlank(message = "Trainer is required")
    private String trainerId;

    @NotBlank(message = "Language is required")
    private String programmingLanguageId;

    // ini untuk apa ??
//    @NotBlank(message = "Trainee is required")
//    private List<String> traineeIds;
}
