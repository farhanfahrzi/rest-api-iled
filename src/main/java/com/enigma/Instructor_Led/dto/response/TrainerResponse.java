package com.enigma.Instructor_Led.dto.response;
import com.enigma.Instructor_Led.entity.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerResponse {
    private String id;
    private String name;
    private String email;
    private Date birthDate;
    private String phoneNumber;
    private String address;
    private String userAccountId;
    private List<String> programmingLanguages;
}
