package com.example.code.services;

import com.example.code.dto.ListUserDTO;
import com.example.code.dto.UserCreationResponse;
import com.example.code.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<ListUserDTO> findAll(Pageable pageable);
    UserDTO findOneById(Long id);
    UserCreationResponse create(UserDTO userDTO);
    void update(UserDTO userDTO, Long id);
    void delete(Long id);
}
