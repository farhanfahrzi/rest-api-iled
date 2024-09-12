package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, String>, JpaSpecificationExecutor<Trainer> {
    Trainer findByUserAccountId(String userAccountId);
}
