package com.example.backend.repositories;

import com.example.backend.models.professionals.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional,Long> {

    Optional<Professional> findByUsername(String username);
}
