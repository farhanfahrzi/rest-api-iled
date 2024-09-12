package com.enigma.Instructor_Led.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentationImageResponse {
    private String id;
    private String link;
    private String scheduleId;
//    private List<String> links;
}
