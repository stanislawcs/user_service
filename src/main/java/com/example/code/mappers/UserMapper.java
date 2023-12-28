package com.example.code.mappers;

import com.example.code.domain.User;
import com.example.code.dto.ListUserDto;
import com.example.code.dto.UserDto;
import com.example.code.mappers.encoder.EncodedMapping;
import com.example.code.mappers.encoder.PasswordEncoderMapper;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = PasswordEncoderMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    ListUserDto toListDto(User entity);

    UserDto toDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedBy = EncodedMapping.class)
    User toEntity(UserDto userDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedBy = EncodedMapping.class)
    void toEntity(UserDto dto, @MappingTarget User entity);
}
