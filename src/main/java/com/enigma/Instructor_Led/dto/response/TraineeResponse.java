package com.enigma.Instructor_Led.dto.response;

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
    private String name;
    private String nik;
    private Date birthDate;
    private String address;
    private String email;
    private String phoneNumber;
    private String batch;
}
