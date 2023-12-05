package com.example.code.services;

import com.example.code.dto.ListUserDTO;

import java.util.List;

public interface UserService {
    List<ListUserDTO> findAll();
    List<ListUserDTO> findAllWithPagination(Integer page, Integer usersPerPage);

}
