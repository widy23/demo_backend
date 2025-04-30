package com.example.backend.services;

import com.example.backend.configuration.RestTemplateConfiguration;
import com.example.backend.models.Users;
import com.example.backend.models.registers.RegisterRequest;
import com.example.backend.repositories.UsersRepository;
import com.example.backend.utils.GlobalExceptionHandler;
import com.example.backend.utils.ResponseHandler;
import com.example.backend.utils.Role;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class MainService {

    private final UsersRepository usersRepository;

    private final RestTemplateConfiguration restTemplateConfiguration;
    public Object updateUsers(Long id, RegisterRequest request) {
        Users user = usersRepository.findById( id)
                .orElseThrow(() -> new GlobalExceptionHandler.UnauthorizedException("User not found"));

        // Actualizar solo los campos enviados
        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getDni() != null) user.setDni(request.getDni());


        usersRepository.save(user);

        return ResponseEntity.ok().body("User updated successfully");
    }

    @CircuitBreaker(name = "findUserService", fallbackMethod = "findUserFallbackService")
    public Object findUser(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseHandler.generateResponse(user);
    }
    public Object findUserFallbackService (Throwable throwable){
        return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }
//    public Object getAllProfessionalsUsers(List<Users> userProf){
//        List<Users>users = usersRepository.findAll();
//        return ResponseEntity.ok(users);
//    }

    public Object deleteUser(Long id){
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        usersRepository.delete(user);
        return ("User deleted successfully");
    }


    public List<Users> getAllProfessionals() {
        return usersRepository.findByRole(Role.PROFESSIONALS);
    }

    public Object getAllUsers() {
        return ResponseEntity.ok(usersRepository.findAll());
    }
}
