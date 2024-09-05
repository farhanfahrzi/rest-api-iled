package com.enigma.Instructor_Led.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgrammingLanguageResponse {
    private String id;
    private String programmingLanguage;
}
