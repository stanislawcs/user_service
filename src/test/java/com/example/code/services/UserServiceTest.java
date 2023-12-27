package com.example.code.services;

import com.example.code.domain.User;
import com.example.code.dto.ListUserDto;
import com.example.code.mappers.UserMapper;
import com.example.code.repositories.UserRepository;
import com.example.code.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper;

    @Test
    void findAll_returnListOfUsers() {

        User user1 = new User(1L, "stanislawcs", "shukans588@gmial.com", "158203", Collections.emptySet());
        User user2 = new User(2L, "marko", "marko@gmail.com", "12345", Collections.emptySet());

        Page<User> userPage = new PageImpl<>(List.of(user1, user2));
        Pageable pageable = PageRequest.of(0, 2);

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        List<ListUserDto> listUserDTOS = userService.findAll(pageable);

        for (ListUserDto list : listUserDTOS) {
            System.out.println(list);
        }

        Assertions.assertNotNull(listUserDTOS);
        Assertions.assertEquals(listUserDTOS, userPage.getContent().stream().map(userMapper::toListDto).toList());

        verify(userRepository).findAll(pageable);
    }

    @Test
    void findAll_returnErrorResponse() {
        Pageable pageable = PageRequest.of(0, 2);

        when(userRepository.findAll()).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> userService.findAll(pageable));

        verify(userRepository).findAll(pageable);
    }

}

