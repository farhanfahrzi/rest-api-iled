package com.enigma.Instructor_Led.entity;

import com.enigma.Instructor_Led.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.SCHEDULE)
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd 00:00")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "topic", nullable = false)
    private String topic;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<DocumentationImage> documentationImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Trainee> traineeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programming_language_id")
    private ProgrammingLanguage programmingLanguage;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<Question> questions;
}
