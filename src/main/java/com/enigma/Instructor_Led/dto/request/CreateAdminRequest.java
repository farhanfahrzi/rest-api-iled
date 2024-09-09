package com.enigma.Instructor_Led.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAdminRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private Date birthDate;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @Pattern(regexp = "^08\\d{9,11}$", message = "nomor telepon hasus valid dan diawali dengan '08' diikuti oleh 9 hingga 11 angka")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "address is required")
    private String address;
}
