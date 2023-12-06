package com.example.code.services.impl;

import com.example.code.domain.User;
import com.example.code.dto.ListUserDTO;
import com.example.code.dto.UserCreationResponse;
import com.example.code.dto.UserDTO;
import com.example.code.exceptions.UserNotFoundException;
import com.example.code.mappers.UserMapper;
import com.example.code.repositories.UserRepository;
import com.example.code.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public List<ListUserDTO> findAll(Pageable pageable) {
        return userRepository
                .findAll(pageable).getContent()
                .stream().map(userMapper::toListDTO).toList();
    }

    @Override
    public UserDTO findOneById(Long id) {
        User user = userRepository
                .findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));

        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserCreationResponse create(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return new UserCreationResponse(user.getId());
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));
        userMapper.toEntity(userDTO, user);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else
            throw new UserNotFoundException("User not found!");
    }

}
