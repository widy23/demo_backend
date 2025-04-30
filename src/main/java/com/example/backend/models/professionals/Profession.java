package com.example.backend.models.professionals;

import com.example.backend.models.professionals.Professional;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "profession")
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(unique = true)
    public int code;
    public  String name;
}
