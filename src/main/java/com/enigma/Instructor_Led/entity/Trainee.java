package com.enigma.Instructor_Led.entity;

import com.enigma.Instructor_Led.constant.ConstantTable;
import com.enigma.Instructor_Led.constant.TraineeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.TRAINEE)
@Builder
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "nik", nullable = false, unique = true)
    private String nik;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "programming_language_id", nullable = true)
    private ProgrammingLanguage programmingLanguage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TraineeStatus status;

    @OneToOne
    @JoinColumn(name = "user_account_id", unique = true, nullable = true) // Allow null values
    private UserAccount userAccount;


}

