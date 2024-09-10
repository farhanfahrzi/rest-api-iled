package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.entity.Trainee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String>, JpaSpecificationExecutor<Schedule> {
    Page<Schedule> findAllByTrainerId(Pageable pageable, String id);

//    @Query(value = "SELECT s.* FROM t_schedule s " +
//            "JOIN t_programming_language pl ON s.programming_language_id = pl.id " +
//            "JOIN m_trainee t ON t.programming_language_id = pl.id " +
//            "WHERE t.id = :id", nativeQuery = true)
   // List<Schedule> findAllByTraineeId(String id);
    @Query("SELECT s FROM Schedule s JOIN s.questions q WHERE q.trainee.id = :traineeId " +
            "AND s.programmingLanguage.id = :programmingLanguageId")
    Page<Schedule> findAllByTraineeIdAndProgrammingLanguageId(Pageable pageable, @Param("traineeId") String traineeId, @Param("programmingLanguageId") String programmingLanguageId);

//    @Query(value = "SELECT s.* FROM t_schedule s " +
//            "JOIN t_programming_language pl ON s.programming_language_id = pl.id " +
//            "JOIN m_trainee t ON t.programming_language_id = pl.id " +
//            "WHERE t.id = :id",
//            countQuery = "SELECT COUNT(*) FROM t_schedule s " +
//                    "JOIN t_programming_language pl ON s.programming_language_id = pl.id " +
//                    "JOIN m_trainee t ON t.programming_language_id = pl.id " +
//                    "WHERE t.id = :id",
//    nativeQuery = true)
//    Page<Schedule> findAllByTraineeId(Pageable pageable, @Param("id") String id);

//    @Query(value = "SELECT s.* FROM t_schedule s JOIN t_programming_language pl " +
//            "ON s.programming_language_id = pl.id WHERE s.date BETWEEN :startDate AND :endDate " +
//            "AND pl.programming_language LIKE :language",
//            nativeQuery = true)
//    List<Schedule> findSchedulesByProgrammingLanguageNameAndDateBetween(@Param("startDate") LocalDateTime startDate,
//                                                                        @Param("endDate") LocalDateTime endDate,
//                                                                        @Param("language") String language);

//    @Query(value = "SELECT s.* FROM t_schedule s JOIN t_programming_language pl " +
//            "ON s.programming_language_id = pl.id WHERE s.date >= :startDate " +
//            "AND pl.programming_language LIKE :language",
//            nativeQuery = true)
//    List<Schedule> findSchedulesByProgrammingLanguageNameAndDateGreaterThanEqual(@Param("startDate") LocalDateTime startDate,
//                                                                        @Param("language") String language);
//
//    @Query(value = "SELECT s.* FROM t_schedule s JOIN t_programming_language pl " +
//            "ON s.programming_language_id = pl.id WHERE s.date <= :endDate " +
//            "AND pl.programming_language LIKE :language",
//            nativeQuery = true)
//    List<Schedule> findSchedulesByProgrammingLanguageNameAndDateLessThanEqual(@Param("endDate") LocalDateTime endDate,
//                                                                            @Param("language") String language);
//
//    @Query(value = "SELECT s.* FROM t_schedule s JOIN t_programming_language pl " +
//            "ON s.programming_language_id = pl.id WHERE pl.programming_language LIKE :language",
//            nativeQuery = true)
//    List<Schedule> findSchedulesByProgrammingLanguageName(@Param("language") String language);
//
//    @Query(value = "SELECT s.* FROM t_schedule s WHERE s.date BETWEEN :startDate AND :endDate",
//            nativeQuery = true)
//    List<Schedule> findSchedulesByDateBetween(@Param("startDate") LocalDateTime startDate,
//                                              @Param("endDate") LocalDateTime endDate);
//
//    @Query(value = "SELECT s.* FROM t_schedule s WHERE s.date >= :startDate",
//            nativeQuery = true)
//    List<Schedule> findSchedulesByDateGreaterThanEqual(@Param("startDate") LocalDateTime startDate);
//
//    @Query(value = "SELECT s.* FROM t_schedule s WHERE s.date <= :endDate",
//            nativeQuery = true)
//    List<Schedule> findSchedulesByDateLessThanEqual(@Param("endDate") LocalDateTime endDate);

//    Page<Schedule> findAll(Specification<Schedule> specification, Pageable pageable);
}
