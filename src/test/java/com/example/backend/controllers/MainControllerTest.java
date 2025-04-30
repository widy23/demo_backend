package com.example.backend.controllers;

import com.example.backend.models.Users;
import com.example.backend.models.registers.RegisterRequest;
import com.example.backend.repositories.UsersRepository;
import com.example.backend.services.AuthService;
import com.example.backend.utils.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RequiredArgsConstructor
@RunWith(MockitoJUnitRunner.StrictStubs.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user", roles = {"CLIENTS"})

class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;

    @Mock
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;
    private Users testUser;

    @BeforeEach
     void setup() {
        usersRepository.deleteAll();

        testUser = Users.builder()
                .username("usuarioTest")
                .email("test@correo.com")
                .password("1234")
                .role(Role.CLIENTS)
                .build();

        testUser = usersRepository.save(testUser);
    }
    @Test
     void testUpdateUserInfo() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .username("user")
                .password("pass")
                .email("email@example.com")
                .dni(12345678L)
                .phone("123456789")
                .role(Role.CLIENTS)
                .professions(List.of("Electricista"))
                .build();
        mockMvc.perform(post("/api/v1/users/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print()) // <--- esto imprime la respuesta
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value("User updated successfully"));

    }

    @Test
     void testFindUser() throws Exception {
        mockMvc.perform(post("/api/v1/find_user/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.username").value(testUser.getUsername()));
    }

    @Test
     void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/delete_user/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
     void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/v1/all_users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body", hasSize(1)));
    }

    @Test
     void testGetAllProfessionals() throws Exception {
        Users professional = Users.builder()
                .username("pro1")
                .email("pro@correo.com")
                .dni(14578962L)
                .password("clave")
                .phone("1235548")
                .role(Role.PROFESSIONALS)
                .build();
        usersRepository.save(professional);

        mockMvc.perform(get("/api/v1/all_professionals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].role", Matchers.everyItem(Matchers.is("PROFESSIONALS"))));
    }




    @Test
    void Create_Test_Client_Controller() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .username("Felipe")
                .email("felipe@gmail.com")
                .password("12345").build();

        Users fakeResponse = Users.builder()
                .username("Felipe")
                .email("felipe@gmail.com")
                .dni(1234567L)
                .role(Role.CLIENTS)
                .build();

        Mockito.when(authService.register(Mockito.any()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(fakeResponse)); // Cambié a CREATED

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }



//        //Arrange
//        Long userId = 1L;
//        RegisterRequest request = new RegisterRequest();
//        RegisterRequest.RegisterRequestBuilder("juancho");
//        request.setEmail("juan@gmail.com");
//        request.setDni(265447896L);
//
//        //Act
//        Map<String, Object> expectedResponse = new HashMap<>();
//        expectedResponse.put("message", "User updated successfully");
//        Mockito.when(mainService.updateUsers(userId, request)).thenReturn(expectedResponse);
//        ResponseEntity<Object> response = mainController.updateUserInfo(userId, request);
//
//        //Assert
//        Assertions (HttpStatus.OK, response.getStatusCode());
//        Assertions.assertTrue(response.getBody() instanceof Map);
//
//        Map<String, Object> body = (Map<String, Object>) response.getBody();
//        Assertions.assertEquals("User updated successfully", body.get("message"));


    @Test
     void test_Controller_Client_Save() throws Exception  {

        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("juancho");
        request.setEmail("juan@gmail.com");
        request.setPassword("mypassword");
        request.setPhone("22211144");
        request.setRole(Role.CLIENTS);
        request.setDni(265447896L);

        // Simula la respuesta esperada del servicio
        Map<String, Object> responseExpected = new HashMap<>();
        responseExpected.put("message", "User added successfully");

        Mockito.when(authService.register(Mockito.any()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(responseExpected));

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.message").value("User added successfully"))
                .andExpect(jsonPath("$.body.status").value("OK"));
    }

}


