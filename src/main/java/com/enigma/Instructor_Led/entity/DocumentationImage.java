package com.enigma.Instructor_Led.entity;

import com.enigma.Instructor_Led.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.DOCUMENTATIONIMAGE)
@Builder
public class DocumentationImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
