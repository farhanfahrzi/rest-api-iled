package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
