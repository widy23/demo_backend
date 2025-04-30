package com.example.backend.models.professionals;

import com.example.backend.models.Users;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professional extends Users {
    public List<String> professions;
}
