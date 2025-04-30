package com.example.backend.models.registers;

import com.example.backend.utils.Role;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Long dni;
    private String phone;
    private Role role;
    private List<String> professions;
}
