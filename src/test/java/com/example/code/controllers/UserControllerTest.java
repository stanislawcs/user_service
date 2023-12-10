package com.example.code.controllers;

import com.example.code.dto.ListUserDTO;
import com.example.code.dto.UserCreationResponse;
import com.example.code.dto.UserDTO;
import com.example.code.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Test
    void findAll_returnListOfUsers() {
        Pageable pageable = PageRequest.of(0, 2);
        ListUserDTO user1 = new ListUserDTO(1L, "stanislawcs", "shukans588@gmail.com");
        ListUserDTO user2 = new ListUserDTO(1L, "marko", "marko@gmail.com");
        List<ListUserDTO> users = List.of(user1, user2);

        when(userService.findAll(pageable)).thenReturn(users);

        ResponseEntity<List<ListUserDTO>> response = userController.findAll(pageable);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(users, response.getBody());

        verify(userService).findAll(pageable);
    }

    @Test
    void findOneById_getUser() {
        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
        when(userService.findOneById(1L)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.findOneById(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(userDTO, response.getBody());

        verify(userService).findOneById(1L);
    }

    @Test
    void create_createUser() {
        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
        UserCreationResponse userCreationResponse = new UserCreationResponse(1L);
        when(userService.create(userDTO)).thenReturn(userCreationResponse);

        ResponseEntity<UserCreationResponse> response = userController.create(userDTO);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(userCreationResponse, response.getBody());

        verify(userService).create(userDTO);
    }

    @Test
    void update_updateUser() {
        UserDTO userDTO = new UserDTO(1L, "stanislawcs", "shukans588@gmail.com", "158203");
        doNothing().when(userService).update(userDTO, 1L);

        ResponseEntity<HttpStatus> response = userController.update(1L, userDTO);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void delete_deleteUser() {
        doNothing().when(userService).delete(1L);

        ResponseEntity<HttpStatus> response = userController.delete(1L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

}
