package com.enigma.Instructor_Led.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDocumentationImageRequest {

    @NotBlank(message = "Schedule id is required")
    private String scheduleId;

//    @NotBlank(message = "Link is required")
//    private String link;

//    @NotBlank
//    @NotNull
//    private MultipartFile image;
}
