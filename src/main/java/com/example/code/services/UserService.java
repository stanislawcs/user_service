package com.example.code.services;

import com.example.code.dto.ListUserDTO;
import com.example.code.dto.UserCreationResponse;
import com.example.code.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<ListUserDTO> findAllWithPagination(Integer page, Integer usersPerPage);
    UserDTO findOneById(Long id);
    UserCreationResponse create(UserDTO userDTO);
}
