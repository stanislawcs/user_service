package com.example.code.controllers;

import com.example.code.dto.ListUserDTO;
import com.example.code.dto.UserCreationResponse;
import com.example.code.dto.UserDTO;
import com.example.code.exceptions.UsernameAlreadyTaken;
import com.example.code.services.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void getAll() {

        ListUserDTO user1 = new ListUserDTO(1L, "stanislawcs", "shukans588@gmail.com");
        ListUserDTO user2 = new ListUserDTO(2L, "marko", "marko@gmail.com");

        List<ListUserDTO> users = List.of(user1, user2);
        when(userService.findAll(PageRequest.of(0, 2))).thenReturn(users);

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

        verify(userService).findAll(PageRequest.of(0, 2));
    }

    @Test
    @SneakyThrows
    void getOneById() {
        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");

        when(userService.findOneById(1L)).thenReturn(userDTO);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("stanislawcs"))
                .andExpect(jsonPath("$.email").value("shukans588@gmail.com"))
                .andExpect(jsonPath("$.password").value("158203"));

        verify(userService).findOneById(1L);

    }

    @Test
    @SneakyThrows
    void create_createUser() {
        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
        when(userService.create(userDTO)).thenReturn(new UserCreationResponse(1L));
        String userJson = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(userService).create(userDTO);
    }

    @Test
    @SneakyThrows
    void create_throwEx() {
        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
        when(userService.create(userDTO)).thenThrow(new UsernameAlreadyTaken());
        String userJson = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());

        verify(userService).create(userDTO);
    }


    @Test
    @SneakyThrows
    void update_updateUser() {
        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
        String userJson = objectMapper.writeValueAsString(userDTO);

        doNothing().when(userService).update(userDTO, 1L);

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isNoContent());

        verify(userService).update(userDTO, 1L);
    }

    @Test
    @SneakyThrows
    void delete_deleteUser() {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userService).delete(1L);
    }
}
