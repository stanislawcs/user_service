package com.example.code.mappers;

import com.example.code.domain.User;
import com.example.code.dto.ListUserDTO;
import com.example.code.dto.UserDTO;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    ListUserDTO toListDTO(User entity);

    UserDTO toDTO(User entity);

    User toEntity(UserDTO userDTO);
}
