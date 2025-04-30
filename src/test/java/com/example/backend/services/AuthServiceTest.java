package com.example.backend.services;

import com.example.backend.AuthResponse;
import com.example.backend.domain.login.LoginRequest;
import com.example.backend.models.Users;
import com.example.backend.repositories.ProfessionRepository;
import com.example.backend.repositories.UsersRepository;
import com.example.backend.utils.Role;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;
    @MockitoBean(types = ProfessionRepository.class)
    private  ProfessionRepository repositoryProfession;
    @Mock
    private  JwtService jwtService;
    @Mock
    private UsersRepository usersRepository;

    @Test
     void login_test_users(){
        String username = "testUser";
        String password = "testPass";

        LoginRequest loginRequest = new LoginRequest(username, password);

        Users mockUser = new Users();
        mockUser.setUsername(username);
        mockUser.setEmail("test@example.com");
        mockUser.setPhone("1234567890");
        mockUser.setRole(Role.CLIENTS);

        String mockToken = "mocked-jwt-token";

        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(jwtService.getToken(mockUser)).thenReturn(mockToken);


        // Act
        AuthResponse response = authService.login(loginRequest);

        // Assert
        assertEquals(mockToken, response.getToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("1234567890", response.getPhone());
        assertEquals(username, response.getUsername());
        assertEquals(Role.CLIENTS, response.getRole());
        // Verify authenticate was called
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usersRepository).findByUsername(username);
        verify(jwtService).getToken(mockUser);
    }

    @Test
    void testLoginUserNotFound() {
        // Arrange
        String username = "notExist";
        String password = "pass";
        LoginRequest loginRequest = new LoginRequest(username, password);

        when(authenticationManager.authenticate(any())).thenReturn(null);

        when(usersRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            authService.login(loginRequest);
        });
        verify(usersRepository).findByUsername(username);
    }
}




