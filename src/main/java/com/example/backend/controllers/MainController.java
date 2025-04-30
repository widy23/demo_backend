package com.example.backend.controllers;

import com.example.backend.models.registers.RegisterRequest;
import com.example.backend.services.MainService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MainController {

    private final MainService service;
    @PostMapping("/users/{id}")
    public ResponseEntity<Object> updateUserInfo (@PathVariable Long id, @RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.updateUsers(id,request));
    }
    @GetMapping("/find_user/{id}")
    public ResponseEntity<Object> findUser (@PathVariable Long id){
        return ResponseEntity.ok(service.findUser(id));
    }

    @DeleteMapping("/delete_user/{id}")
    public  ResponseEntity<Object> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteUser(id));
    }

    @GetMapping("/all_professionals")
    public ResponseEntity<Object> getAllProfessionalUser(){
        return ResponseEntity.ok(service.getAllProfessionals());
    }
    @GetMapping("/all_users")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }
}
