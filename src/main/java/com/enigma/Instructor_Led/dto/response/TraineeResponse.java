package com.enigma.Instructor_Led.dto.response;

import com.enigma.Instructor_Led.constant.TraineeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraineeResponse {
    private String id;
    private String nik;
    private String name;
    private Date birthDate;
    private String address;
    private String email;
    private String phoneNumber;
    private String programmingLanguage;
    private TraineeStatus status;
    private String username;
    private boolean isPasswordUpdated;
}
