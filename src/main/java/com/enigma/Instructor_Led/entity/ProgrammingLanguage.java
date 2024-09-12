package com.enigma.Instructor_Led.entity;

import com.enigma.Instructor_Led.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.PROGRAMMINGLANGUAGE)
@Builder
public class ProgrammingLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "programming_language")
    private String programmingLanguage;

//    @ManyToOne
//    @JoinColumn(name = "trainer_id") // Column untuk menyimpan reference ke Trainer
//    private Trainer trainer;

    @OneToMany(mappedBy = "programmingLanguage")
    private List<Trainee> trainees;

    @ToString.Exclude
    @JsonBackReference
    @ManyToMany(mappedBy = "programmingLanguages")
    private List<Trainer> trainer;
}

