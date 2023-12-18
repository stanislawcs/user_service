package com.example.code.controllers;

import com.example.code.services.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerIntegrationTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.5");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("postgres.driver",
                postgres::getDriverClassName);
    }

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAll_returnListOfUsers() {

        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].username").value("stanislawcs"))
                .andExpect(jsonPath("$.[0].email").value("shukans588@gmail.com"))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[1].username").value("marko"))
                .andExpect(jsonPath("$.[1].email").value("marko@gmail.com"));

    }

    @Test
    @SneakyThrows
    void findAll_returnErrorResponse() {

        mockMvc.perform(get("/users")
                        .param("page", "")
                        .param("size", "2"))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    @SneakyThrows
//    void findOneById_returnUser() {
//        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
//
//        when(userService.findOneById(1L)).thenReturn(userDTO);
//
//        mockMvc.perform(get("/users/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.username").value("stanislawcs"))
//                .andExpect(jsonPath("$.email").value("shukans588@gmail.com"))
//                .andExpect(jsonPath("$.password").value("158203"));
//
//        verify(userService).findOneById(1L);
//
//    }
//
//    @Test
//    @SneakyThrows
//    void findOneById_returnErrorResponse() {
//
//        when(userService.findOneById(1L)).thenThrow(new UserNotFoundException("User not found!"));
//
//        mockMvc.perform(get("/users/{id}", 1L))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("User not found!"));
//
//        verify(userService).findOneById(1L);
//    }
//
//    @Test
//    @SneakyThrows
//    void create_createUser() {
//        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
//        when(userService.create(userDTO)).thenReturn(new UserCreationResponse(1L));
//        String userJson = objectMapper.writeValueAsString(userDTO);
//
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1));
//
//        verify(userService).create(userDTO);
//    }
//
//    @Test
//    @SneakyThrows
//    void create_returnErrorResponse() {
//        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
//        when(userService.create(userDTO)).thenThrow(new UsernameAlreadyTaken("Username already taken"));
//        String userJson = objectMapper.writeValueAsString(userDTO);
//
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Username already taken"));
//
//        verify(userService).create(userDTO);
//    }
//
//    @Test
//    @SneakyThrows
//    void update_updateUser() {
//        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
//        String userJson = objectMapper.writeValueAsString(userDTO);
//
//        doNothing().when(userService).update(userDTO, 1L);
//
//        mockMvc.perform(put("/users/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isNoContent());
//
//        verify(userService).update(userDTO, 1L);
//    }
//
//    @Test
//    @SneakyThrows
//    void update_returnErrorResponse() {
//        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
//        String userJson = objectMapper.writeValueAsString(userDTO);
//
//        doThrow(new UserNotFoundException("User not found!")).when(userService).update(userDTO, 1L);
//
//        mockMvc.perform(put("/users/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("User not found!"));
//
//        verify(userService).update(userDTO, 1L);
//    }
//
//    @Test
//    @SneakyThrows
//    void delete_deleteUser() {
//        doNothing().when(userService).delete(1L);
//
//        mockMvc.perform(delete("/users/{id}", 1L))
//                .andExpect(status().isNoContent());
//
//        verify(userService).delete(1L);
//    }
//
//    @Test
//    @SneakyThrows
//    void delete_returnErrorResponse() {
//        doThrow(new UserNotFoundException("User not found!")).when(userService).delete(1L);
//
//        mockMvc.perform(delete("/users/{id}", 1L))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("User not found!"));
//
//        verify(userService).delete(1L);
//    }
}