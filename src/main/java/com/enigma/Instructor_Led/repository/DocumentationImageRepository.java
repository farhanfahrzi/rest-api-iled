package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.DocumentationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentationImageRepository extends JpaRepository<DocumentationImage, String> {
}
