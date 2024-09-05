package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, String> {
}
