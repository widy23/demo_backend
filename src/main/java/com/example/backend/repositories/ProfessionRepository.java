package com.example.backend.repositories;

import com.example.backend.models.professionals.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession,Long> {
    Optional<Profession> findByCode(int code);

   boolean existsByName(String name);
}
