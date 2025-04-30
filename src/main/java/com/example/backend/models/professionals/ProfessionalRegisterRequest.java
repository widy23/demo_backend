package com.example.backend.models.professionals;


import com.example.backend.models.registers.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalRegisterRequest extends RegisterRequest {

    List<String> professions ;
}
