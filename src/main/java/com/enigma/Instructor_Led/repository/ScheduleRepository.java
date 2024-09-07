package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findAllByTrainerId(String id);

    @Query(value = "SELECT s.* from t_schedule s WHERE s.id = :id", nativeQuery = true)
    Schedule findByScheduleId(String id);

    @Query(value = "SELECT s.* FROM t_schedule s JOIN t_programming_language pl " +
            "ON s.programming_language_id = pl.id WHERE s.date BETWEEN :startDate AND :endDate " +
            "AND pl.programming_language LIKE %:language%",
            nativeQuery = true)
    List<Schedule> findSchedulesByProgrammingLanguageNameAndDateBetween(@Param("startDate") Date startDate,
                                                                        @Param("endDate") Date endDate,
                                                                        @Param("language") String language);

    @Query(value = "SELECT s.* FROM t_schedule s JOIN t_programming_language pl " +
            "ON s.programming_language_id = pl.id WHERE s.date >= :startDate " +
            "AND pl.programming_language LIKE %:language%",
            nativeQuery = true)
    List<Schedule> findSchedulesByProgrammingLanguageNameAndDateGreaterThanEqual(@Param("startDate") Date startDate,
                                                                        @Param("language") String language);

    @Query(value = "SELECT s.* FROM t_schedule s JOIN t_programming_language pl " +
            "ON s.programming_language_id = pl.id WHERE s.date <= :endDate " +
            "AND pl.programming_language LIKE %:language%",
            nativeQuery = true)
    List<Schedule> findSchedulesByProgrammingLanguageNameAndDateLessThanEqual(@Param("endDate") Date endDate,
                                                                            @Param("language") String language);

    @Query(value = "SELECT s.* FROM t_schedule s JOIN t_programming_language pl " +
            "ON s.programming_language_id = pl.id WHERE pl.programming_language LIKE %:language%",
            nativeQuery = true)
    List<Schedule> findSchedulesByProgrammingLanguageName(@Param("language") String language);

    @Query(value = "SELECT s.* FROM t_schedule s WHERE s.date BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    List<Schedule> findSchedulesByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT s.* FROM t_schedule s WHERE s.date >= :startDate",
            nativeQuery = true)
    List<Schedule> findSchedulesByDateGreaterThanEqual(@Param("startDate") Date startDate);

    @Query(value = "SELECT s.* FROM t_schedule s WHERE s.date <= :endDate",
            nativeQuery = true)
    List<Schedule> findSchedulesByDateLessThanEqual(@Param("endDate") Date endDate);
}
