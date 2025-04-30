package com.example.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    // Manejo de conflicto cuando el usuario ya existe
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleConflictException(UserAlreadyExistsException ex) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT);
    }

    // Manejo de error de autenticación
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    // Manejo de errores generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return buildErrorResponse(ex, HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<Object> buildErrorResponse(Exception exception,HttpStatus status){
        Map <String ,Object> errorTitle = new HashMap<>();
        Map <String ,String> response = new HashMap<>();
        response.put("description",exception.getMessage());
        errorTitle.put("error", response);
        return ResponseEntity.status(status).body(errorTitle);
    }

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }

    }
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }


}
