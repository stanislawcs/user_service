package com.example.code.services;

import com.example.code.dto.ListUserDto;
import com.example.code.dto.UserCreationResponse;
import com.example.code.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<ListUserDto> findAll(Pageable pageable);

    UserDto findById(Long id);

    UserCreationResponse create(UserDto userDTO);

    void update(UserDto userDTO, Long id);

    void delete(Long id);
}
