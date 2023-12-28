package com.example.code.services.impl;

import com.example.code.domain.Role;
import com.example.code.domain.RoleType;
import com.example.code.domain.User;
import com.example.code.dto.AuthenticationResponse;
import com.example.code.dto.ListUserDto;
import com.example.code.dto.TokenType;
import com.example.code.dto.UserDto;
import com.example.code.exceptions.ObjectAlreadyExistsException;
import com.example.code.exceptions.UserNotFoundException;
import com.example.code.mappers.UserMapper;
import com.example.code.repositories.JwtTokenRepositoryImpl;
import com.example.code.repositories.RoleRepository;
import com.example.code.repositories.UserRepository;
import com.example.code.services.UserService;
import com.example.code.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenRepositoryImpl jwtTokenRepository;

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

    public AuthenticationResponse create(UserDto dto) {
        log.debug("UserService: create()");
        Optional<User> foundedUser = userRepository.findByUsername(dto.getUsername());

        if (foundedUser.isPresent()) {
            throw new ObjectAlreadyExistsException("User already exists!");
        }

        User user = userMapper.toEntity(dto);
        Role role = roleRepository.findRoleByType(RoleType.ROLE_READER);

        user.addRole(role);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        saveToken(token, user.getId());

        return AuthenticationResponse
                .builder()
                .id(user.getId())
                .token(token)
                .build();
    }

    @Override
    @Transactional

    public void update(UserDto dto, Long id) {
        log.debug("UserService: update()");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));

        userMapper.toEntity(dto, user);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        log.debug("UserService: delete()");
        userRepository.deleteById(id);
    }

    private void saveToken(String token, Long userId) {
        String tokenId = jwtService.extractId(token);
        String key = userId + ":" + TokenType.ACCESS.toString().toLowerCase() + ":" +
                tokenId;
        Date expiredTokenTime = jwtService.extractExpiration(token);
        jwtTokenRepository.save(key, token, expiredTokenTime.getTime());

    }
}
