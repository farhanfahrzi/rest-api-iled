package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findByTraineeId(String id);

    @Query(value = "SELECT q.* FROM t_question q " +
            "JOIN t_schedule s ON q.schedule_id = s.id " +
            "WHERE s.trainer_id = :trainerId",
            nativeQuery = true)
    List<Question> findByTrainerId(String trainerId);
}
