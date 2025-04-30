package com.example.backend.services;

import com.example.backend.models.Users;
import com.example.backend.models.registers.RegisterRequest;
import com.example.backend.repositories.UsersRepository;
import com.example.backend.utils.Role;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.StrictStubs.class)
@AutoConfigureMockMvc(addFilters = false)
public class MainServiceTest {
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private MainService mainService;

    @Test
    public void testUpdateUsers_successfullyUpdatesUser() {
        Long userId = 1L;
        Users existingUser = new Users();
        existingUser.setId(userId);
        existingUser.setUsername("usernameActual");
        existingUser.setEmail("eamilactual@example.com");

        RegisterRequest request = new RegisterRequest();
        request.setUsername("nuevousuario");
        request.setEmail("nuevoemail@example.com");

        when(usersRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        Object response = mainService.updateUsers(userId, request);

        assertEquals("nuevousuario", existingUser.getUsername());
        assertEquals("nuevoemail@example.com", existingUser.getEmail());

        verify(usersRepository).save(existingUser);

        assertTrue(response instanceof ResponseEntity);
        assertEquals("User updated successfully", ((ResponseEntity<?>) response).getBody());
    }

    @Test
    public void testUpdateUsers_userNotFound_throwsException() {
        Long userId = 1L;
        RegisterRequest request = new RegisterRequest();
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mainService.updateUsers(userId, request);
        });

        assertEquals("User not found", exception.getMessage());
    }
    @Test
    public void testFindUser_success() {
        Users user = new Users();
        user.setId(1L);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        Object response = mainService.findUser(1L);

        assertTrue(response instanceof ResponseEntity);
        assertEquals(user, ((ResponseEntity<?>) response).getBody());
    }

    @Test
    public void testFindUser_notFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> mainService.findUser(1L));
    }

    // Test para getAllProfessionalsUsers
//    @Test
//    public void testGetAllProfessionalsUsers_returnsAllUsers() {
//        Users u1 = new Users();
//        Users u2 = new Users();
//        when(usersRepository.findAll()).thenReturn(Arrays.asList(u1, u2));
//
//        Object response = mainService.getAllProfessionalsUsers(null);
//        assertTrue(response instanceof ResponseEntity);
//        assertEquals(2, ((List<?>) ((ResponseEntity<?>) response).getBody()).size());
//    }

    // Test para deleteUser
    @Test
    public void testDeleteUser_success() {
        Long id = 1L;
        Users user = new Users();
        user.setId(id);

        when(usersRepository.findById(id)).thenReturn(Optional.of(user));

        Object response = mainService.deleteUser(id);

        verify(usersRepository).delete(user);

        assertEquals("User deleted successfully", response);
    }

    @Test
    public void testDeleteUser_userNotFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> mainService.deleteUser(1L));
    }

    // Test para getAllProfessionals
    @Test
    public void testGetAllProfessionals_returnsOnlyProfessionals() {
        Users pro1 = new Users();
        Users pro2 = new Users();

        when(usersRepository.findByRole(Role.PROFESSIONALS)).thenReturn(List.of(pro1, pro2));

        List<Users> result = mainService.getAllProfessionals();
        assertEquals(2, result.size());
    }

    // Test para getAllUsers
    @Test
    public void testGetAllUsers_returnsAll() {
        Users u1 = new Users();
        Users u2 = new Users();
        when(usersRepository.findAll()).thenReturn(List.of(u1, u2));

        Object response = mainService.getAllUsers();
        assertTrue(response instanceof ResponseEntity);
        assertEquals(2, ((List<?>) ((ResponseEntity<?>) response).getBody()).size());
    }
}