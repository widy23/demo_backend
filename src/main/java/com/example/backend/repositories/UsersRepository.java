package com.example.backend.repositories;

import com.example.backend.models.Users;
import com.example.backend.utils.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional <Users> findByUsername(String username);

    Optional<Users> findById(Long id);


    List<Users> findAll();
    List<Users> findByRole(Role role);
}
