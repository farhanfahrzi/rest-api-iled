package com.enigma.Instructor_Led.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
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
public class CreateTrainerRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotNull(message = "Birth date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    // @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "format tanggal harus 'yyyy-MM-dd'")
    private Date birthDate;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

//    @NotEmpty(message = "Programming Language")
//    private List<String> programmingLanguages;

}
