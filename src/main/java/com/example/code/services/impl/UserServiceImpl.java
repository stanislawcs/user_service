package com.example.code.services.impl;

import com.example.code.dto.ListUserDTO;
import com.example.code.mappers.UserMapper;
import com.example.code.repositories.UserRepository;
import com.example.code.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<ListUserDTO> getAll() {
        return userRepository.findAll().
                stream().map(userMapper::toListDTO).toList();
    }
}
