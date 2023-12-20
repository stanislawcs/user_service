package com.example.code.services.impl;

import com.example.code.domain.User;
import com.example.code.dto.ListUserDTO;
import com.example.code.dto.UserCreationResponse;
import com.example.code.dto.UserDTO;
import com.example.code.exceptions.UserNotFoundException;
import com.example.code.exceptions.UsernameAlreadyTakenException;
import com.example.code.mappers.UserMapper;
import com.example.code.repositories.UserRepository;
import com.example.code.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Profile("local")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ListUserDTO> findAll(Pageable pageable) {
        log.info("UserService: findAll()");
        return userRepository
                .findAll(pageable).getContent()
                .stream().map(userMapper::toListDTO).toList();
    }

    @Override
    public UserDTO findById(Long id) {
        log.info("UserService: findOneById()");

        User user = userRepository
                .findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));

        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserCreationResponse create(UserDTO userDTO) {
        log.info("UserService: create()");
        Optional<User> foundedUser = userRepository.findByUsername(userDTO.getUsername());

        if (foundedUser.isPresent()) {
            throw new UsernameAlreadyTakenException("Username already taken");
        } else {
            User user = userMapper.toEntity(userDTO);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);
            return new UserCreationResponse(user.getId());
        }

    }

    @Override
    @Transactional
    public void update(UserDTO userDTO, Long id) {
        log.info("UserService: update()");
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));

        userMapper.toEntity(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("UserService: delete()");

        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else
            throw new UserNotFoundException("User not found!");

    }

}
