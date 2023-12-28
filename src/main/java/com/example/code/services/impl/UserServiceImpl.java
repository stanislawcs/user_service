package com.example.code.services.impl;

import com.example.code.domain.Role;
import com.example.code.domain.RoleType;
import com.example.code.domain.User;
import com.example.code.dto.AuthenticationResponse;
import com.example.code.dto.ListUserDto;
import com.example.code.dto.UserDto;
import com.example.code.exceptions.ObjectAlreadyExistsException;
import com.example.code.exceptions.UserNotFoundException;
import com.example.code.mappers.UserMapper;
import com.example.code.repositories.UserRepository;
import com.example.code.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<ListUserDto> findAll(Pageable pageable) {
        log.debug("UserService: findAll()");
        return userRepository
                .findAll(pageable).getContent()
                .stream().map(userMapper::toListDto).toList();
    }

    @Override
    public UserDto findById(Long id) {
        log.debug("UserService: findById()");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public AuthenticationResponse create(UserDto userDTO) {
        log.debug("UserService: create()");
        Optional<User> foundedUser = userRepository.findByUsername(userDTO.getUsername());

        if (foundedUser.isPresent()) {
            throw new ObjectAlreadyExistsException("User already exists!");
        }

        User user = userMapper.toEntity(userDTO);
        user.setRoles(Set.of(Role.builder().type(RoleType.ROLE_READER).build()));

        userRepository.save(user);

        return AuthenticationResponse
                .builder()
                .id(user.getId())
                .build();
    }

    @Override
    @Transactional
    public void update(UserDto userDTO, Long id) {
        log.debug("UserService: update()");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));

        userMapper.toEntity(userDTO, user);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        log.debug("UserService: delete()");
        userRepository.deleteById(id);
    }

}
