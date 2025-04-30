package com.example.backend.services;

import com.example.backend.AuthResponse;
import com.example.backend.domain.login.LoginRequest;
import com.example.backend.models.Users;
import com.example.backend.models.registers.RegisterRequest;
import com.example.backend.repositories.ProfessionRepository;
import com.example.backend.repositories.UsersRepository;
import com.example.backend.utils.GlobalExceptionHandler;
import com.example.backend.utils.ResponseHandler;
import com.example.backend.utils.Role;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private  final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ProfessionRepository repositoryProfession;
    private  final JwtService jwtService;
    private  final UsersRepository usersRepository;

    @CircuitBreaker(name = "loginService", fallbackMethod = "fallback")
    public AuthResponse login(LoginRequest loginRequest) {
//        if ("erroruser".equalsIgnoreCase(loginRequest.username)){
//            throw new RuntimeException("Error simulado para activar el circuit breaker");
//        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username,loginRequest.password));
        Users user = usersRepository.findByUsername(loginRequest.username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .phone(user.getPhone())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
    public AuthResponse fallback(Throwable t) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }


    public ResponseEntity<Object> register(RegisterRequest registerRequest){
    if (usersRepository.findByUsername(registerRequest.getUsername()).isPresent()){
        throw  new GlobalExceptionHandler.UserAlreadyExistsException ("USERNAME ALREADY EXIST");
    }

        List<String> professionNames = registerRequest.getProfessions();
        if (professionNames == null) {
            professionNames = Collections.emptyList();
        }

        List<String> validNames = professionNames.stream()
                .filter(repositoryProfession::existsByName)
                .toList();


        if (validNames.size() != professionNames.size()) {
            throw new GlobalExceptionHandler.UnauthorizedException("One or more profession names must created.");
        }
    Users users = Users.builder()
            .username(registerRequest.getUsername())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .email(registerRequest.getEmail())
            .role(registerRequest.getRole())
            .dni(registerRequest.getDni())
            .phone(registerRequest.getPhone())
            .professions(registerRequest.getProfessions())
            .build();
            usersRepository.save(users);

        return ResponseHandler.generateRegisterResponse(
                "User added successfully",HttpStatus.OK);
    }




}

    ///public ResponseEntity<Object>

