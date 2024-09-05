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

    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "topic")
    private String topic;

    @OneToMany(mappedBy = "schedule")
    private List<DocumentationImage> documentationImages;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}