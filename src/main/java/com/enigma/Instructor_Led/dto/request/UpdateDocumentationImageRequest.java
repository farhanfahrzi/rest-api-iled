package com.enigma.Instructor_Led.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDocumentationImageRequest {

    @NotBlank(message = "Schedule id is required")
    private String scheduleId;

    @NotNull(message = "Link is required")
    private List<String> link;

    @NotNull
    private List<String> id;
}
