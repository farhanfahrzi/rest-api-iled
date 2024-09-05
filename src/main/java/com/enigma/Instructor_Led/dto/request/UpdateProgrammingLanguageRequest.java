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
public class UpdateProgrammingLanguageRequest {
    @NotBlank(message = "Id is required")
    private String id;
    @NotBlank(message = "Language is required")
    private String language;
}
