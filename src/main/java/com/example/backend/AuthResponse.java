package com.example.backend;

import com.example.backend.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private String email;
    private String phone;
    private Role role;
}
