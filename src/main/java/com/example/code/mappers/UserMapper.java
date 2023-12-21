package com.example.code.mappers;

import com.example.code.domain.User;
import com.example.code.dto.ListUserDto;
import com.example.code.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    ListUserDto toListDto(User entity);

    UserDto toDto(User entity);

    User toEntity(UserDto userDTO);

    @Mapping(target = "id", ignore = true)
    void toEntity(UserDto dto, @MappingTarget User entity);
}
