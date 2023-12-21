package com.example.code.services.impl;

import com.example.code.domain.User;
import com.example.code.dto.ListUserDto;
import com.example.code.dto.UserCreationResponse;
import com.example.code.dto.UserDto;
import com.example.code.exceptions.ObjectAlreadyExistsException;
import com.example.code.exceptions.UserNotFoundException;
import com.example.code.mappers.UserMapper;
import com.example.code.repositories.UserRepository;
import com.example.code.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("local")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ListUserDto> findAll(Pageable pageable) {
        log.info("UserService: findAll()");
        return userRepository
                .findAll(pageable).getContent()
                .stream().map(userMapper::toListDto).toList();
    }

    @Override
    public UserDto findById(Long id) {
        log.info("UserService: findById()");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserCreationResponse create(UserDto userDTO) {
        log.info("UserService: create()");
        Optional<User> foundedUser = userRepository.findByUsername(userDTO.getUsername());

        if (foundedUser.isPresent()) {
            throw new ObjectAlreadyExistsException("User already exists!");
        }
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return new UserCreationResponse(user.getId());


    }

    @Override
    @Transactional
    public void update(UserDto userDTO, Long id) {
        log.info("UserService: update()");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));

        userMapper.toEntity(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("UserService: delete()");
        userRepository.deleteById(id);
    }

}
