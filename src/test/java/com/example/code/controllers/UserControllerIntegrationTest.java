package com.example.code.controllers;

import com.example.code.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.5");

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class UserFindTest {

        @Test
        @SneakyThrows
        @DisplayName("should find all users")
        void findAll_returnListOfUsers() {

            mockMvc.perform(get("/users")
                            .param("page", "0")
                            .param("size", "2")
                            .with(httpBasic("stanislawcs", "1500002006501")))
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
        void findAll_unauthorized() {

            mockMvc.perform(get("/users")
                            .param("page", "0")
                            .param("size", "2"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @SneakyThrows
        void findAll_forbidden() {

            mockMvc.perform(get("/users")
                            .param("page", "0")
                            .param("size", "2")
                            .with(httpBasic("marko", "12345")))
                    .andExpect(status().isForbidden());
        }

        @Test
        @SneakyThrows
        void findOneById_returnUser() {

            mockMvc.perform(get("/users/{id}", 1L)
                            .with(httpBasic("stanislawcs", "1500002006501")))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.username").value("stanislawcs"))
                    .andExpect(jsonPath("$.email").value("shukans588@gmail.com"))
                    .andExpect(jsonPath("$.password").value("$2a$12$gAUlWvItcnv4rdGLS/Opwu16z1UuGImOprAkf99uRdUM7y4W.W/Ta"));

        }

        @Test
        @SneakyThrows
        void findOneById_notFound() {

            mockMvc.perform(get("/users/{id}", Integer.MAX_VALUE)
                            .with(httpBasic("stanislawcs", "1500002006501")))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("User with id: %s not found!".formatted(Integer.MAX_VALUE)));

        }
    }

    @Nested
    class UserCreateTest {

        @Test
        @SneakyThrows
        void create_createUser() {
            UserDto dto = new UserDto(12L, "ancient", "ancient@gmail.com", "158203");
            ObjectMapper objectMapper = new ObjectMapper();
            String dtoJson = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dtoJson)
                            .with(httpBasic("stanislawcs", "1500002006501")))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(12L));

        }

        @Test
        @SneakyThrows
        void create_userAlreadyExists() {
            UserDto dto = new UserDto(1L, "stanislawcs", "shukans588@gmail.com", "1500002006501");

            ObjectMapper objectMapper = new ObjectMapper();
            String dtoJson = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dtoJson)
                            .with(httpBasic("stanislawcs", "1500002006501")))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("User already exists!"));
        }
    }

    @Nested
    class UserUpdateTest {

        @Test
        @SneakyThrows
        void update_updateUser() {
            UserDto dto = new UserDto(11L, "username", "email@gmail.com", "password");
            ObjectMapper objectMapper = new ObjectMapper();
            String dtoJson = objectMapper.writeValueAsString(dto);

            mockMvc.perform(put("/users/{id}", 11L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dtoJson)
                            .with(httpBasic("stanislawcs", "1500002006501")))
                    .andExpect(status().isOk());

        }

        @Test
        @SneakyThrows
        void update_notFound() {
            UserDto dto = new UserDto(11L, "username", "email@gmail.com", "password");
            ObjectMapper objectMapper = new ObjectMapper();
            String dtoJson = objectMapper.writeValueAsString(dto);

            mockMvc.perform(put("/users/{id}", Integer.MAX_VALUE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dtoJson)
                            .with(httpBasic("stanislawcs", "1500002006501")))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("User with id: %s not found!".formatted(Integer.MAX_VALUE)));
        }
    }

    @Nested
    class UserDeleteTest {

        @Test
        @SneakyThrows
        void delete_deleteUser() {

            mockMvc.perform(delete("/users/{id}", 5L)
                            .with(httpBasic("stanislawcs", "1500002006501")))
                    .andExpect(status().isNoContent());
        }
    }


}
