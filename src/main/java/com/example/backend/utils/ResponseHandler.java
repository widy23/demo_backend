package com.example.backend.utils;

import com.example.backend.models.Users;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Builder
public class ResponseHandler {

    public static ResponseEntity<Object> generateRegisterResponse(String message, HttpStatus status){
        Map<String, Object> map = new HashMap<>() ;
        map.put("message",message);
        map.put("status",status);
        return new ResponseEntity<>(map,status);
    }
    public static ResponseEntity<Object> generateLoginResponse(Users users, HttpStatus status){
        Map<String, Object> map = new HashMap<>() ;
        map.put("username",users.getUsername());
        return new ResponseEntity<>(map,status);
    }

    public static ResponseEntity<Object> generateResponse(Users users) {
        Map<String, Object> map = new HashMap<>() ;
            map.put("username",users.getUsername());
            map.put("email",users.getEmail());
            map.put("dni",users.getDni());
            map.put("phone",users.getPhone());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    public static ResponseEntity<Object> generateDefaultResponse(String message) {
        Map<String, Object> map = new HashMap<>() ;
        map.put("message",message);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
