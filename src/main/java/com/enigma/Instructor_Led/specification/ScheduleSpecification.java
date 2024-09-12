package com.enigma.Instructor_Led.specification;

import com.enigma.Instructor_Led.entity.Schedule;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ScheduleSpecification {
    public static Specification<Schedule> hasLanguage(String language) {
        return (root, query, cb) -> {
            var languageJoin = root.join("programmingLanguage", JoinType.INNER);
            var languagePredicate = cb.like(cb.lower(languageJoin.get("programmingLanguage")), "%" + language.toLowerCase() + "%");
            return cb.and(languagePredicate);
        };
    }

    public static Specification<Schedule> hasStartDate(Date startDate) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), startDate);
    }

    public static Specification<Schedule> hasEndDate(Date endDate) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), endDate);
    }

    public static Specification<Schedule> hasLanguageAndStartDate(String language, Date startDate) {
        return (root, query, cb) -> {
            var languageJoin = root.join("programmingLanguage", JoinType.INNER);
            var languagePredicate = cb.like(cb.lower(languageJoin.get("programmingLanguage")), "%" + language.toLowerCase() + "%");
            var startDatePredicate = cb.greaterThanOrEqualTo(root.get("date"), startDate);
            return cb.and(languagePredicate, startDatePredicate);
        };
    }

    public static Specification<Schedule> hasLanguageAndEndDate(String language, Date endDate) {
        return (root, query, cb) -> {
            var languageJoin = root.join("programmingLanguage", JoinType.INNER);
            var languagePredicate = cb.like(cb.lower(languageJoin.get("programmingLanguage")), "%" + language.toLowerCase() + "%");
            var endDatePredicate = cb.greaterThanOrEqualTo(root.get("date"), endDate);
            return cb.and(languagePredicate, endDatePredicate);
        };
    }

    public static Specification<Schedule> hasStartDateAndEndDate(Date startDate, Date endDate) {
        return (root, query, cb) -> {
            var startDatePredicate = cb.greaterThanOrEqualTo(root.get("date"), startDate);
            var endDatePredicate = cb.greaterThanOrEqualTo(root.get("date"), endDate);
            return cb.and(startDatePredicate, endDatePredicate);
        };
    }

    public static Specification<Schedule> hasLanguageAndStartDateAndEndDate(String language, Date startDate, Date endDate) {
        return (root, query, cb) -> {
            var languageJoin = root.join("programmingLanguage", JoinType.INNER);
            var languagePredicate = cb.like(cb.lower(languageJoin.get("programmingLanguage")), "%" + language.toLowerCase() + "%");
            var startDatePredicate = cb.greaterThanOrEqualTo(root.get("date"), startDate);
            var endDatePredicate = cb.greaterThanOrEqualTo(root.get("date"), endDate);
            return cb.and(languagePredicate, startDatePredicate, endDatePredicate);
        };
    }
}
