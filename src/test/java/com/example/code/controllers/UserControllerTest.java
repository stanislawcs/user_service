package com.example.code.controllers;

import com.example.code.dto.AuthenticationResponse;
import com.example.code.dto.ListUserDto;
import com.example.code.dto.UserDto;
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
        ListUserDto dto1 = new ListUserDto(1L, "stanislawcs", "shukans588@gmail.com");
        ListUserDto dto2 = new ListUserDto(2L, "marko", "marko@gmail.com");
        List<ListUserDto> users = List.of(dto1, dto2);

        when(userService.findAll(pageable)).thenReturn(users);

        ResponseEntity<List<ListUserDto>> response = userController.findAll(pageable);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(users, response.getBody());

        verify(userService).findAll(pageable);
    }

    @Test
    void findById_getUser() {
        UserDto dto = new UserDto(1L, "stanislawcs", "shukans588@gmail.com", "12345");
        when(userService.findById(1L)).thenReturn(dto);

        ResponseEntity<UserDto> response = userController.findById(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(dto, response.getBody());

        verify(userService).findById(1L);
    }

    @Test
    void create_createUser() {
        UserDto userDTO = new UserDto(1L, "stanislawcs", "shukans588@gmail.com", "12345");
        AuthenticationResponse userCreationResponse = AuthenticationResponse.builder().id(1L).build();

        when(userService.create(userDTO)).thenReturn(userCreationResponse);

        ResponseEntity<AuthenticationResponse> response = userController.create(userDTO);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(userCreationResponse, response.getBody());
        verify(userService).create(userDTO);
    }

    @Test
    void update_updateUser() {
        UserDto userDTO = new UserDto(1L, "stanislawcs", "shukans588@gmail.com", "12345");
        doNothing().when(userService).update(userDTO, 1L);

        ResponseEntity<HttpStatus> response = userController.update(1L, userDTO);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
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
