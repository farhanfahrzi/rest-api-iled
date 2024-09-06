package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, String> {
}
