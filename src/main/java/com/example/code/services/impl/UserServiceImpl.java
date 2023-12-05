package com.example.code.services.impl;

import com.example.code.dto.ListUserDTO;
import com.example.code.mappers.UserMapper;
import com.example.code.repositories.UserRepository;
import com.example.code.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<ListUserDTO> findAll() {
        return userRepository.findAll().
                stream().map(userMapper::toListDTO).toList();
    }

    @Override
    public List<ListUserDTO> findAllWithPagination(Integer page, Integer usersPerPage) {
        return userRepository
                .findAll(PageRequest.of(page, usersPerPage)).getContent()
                .stream().map(userMapper::toListDTO).toList();
    }

}
