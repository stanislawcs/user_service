package com.example.code.services;

import com.example.code.dto.AuthenticationResponse;
import com.example.code.dto.UserDto;

public interface AuthService {

    AuthenticationResponse login(UserDto dto);
}
