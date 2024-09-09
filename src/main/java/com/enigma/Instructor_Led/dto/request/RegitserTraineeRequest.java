package com.enigma.Instructor_Led.dto.request;

import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegitserTraineeRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "NIK is required")
    @Pattern(regexp = "^(1[1-9]|21|[37][1-6]|5[1-3]|6[1-5]|[89][12])\\d{2}\\d{2}([04][1-9]|[1256][0-9]|[37][01])(0[1-9]|1[0-2])\\d{2}\\d{4}$")
    private String nik;

    @NotNull(message = "Birth date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@gmail\\.com$", message = "email harus menggunakan domain @gmail.com")
    @Email
    private String email;

    @Pattern(regexp = "^08\\d{9,11}$", message = "nomor telepon harus valid dan diawali dengan '08' diikuti oleh 9 hingga 11 angka")
    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

}
