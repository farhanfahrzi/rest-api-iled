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
public class UpdateDocumentationImageRequest {

    @NotBlank(message = "Schedule id is required")
    private String scheduleId;
    @NotBlank(message = "Link is required")
    private String link;
}
