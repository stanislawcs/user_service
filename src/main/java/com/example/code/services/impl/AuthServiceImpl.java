package com.example.code.services.impl;

import com.example.code.domain.User;
import com.example.code.dto.AuthenticationResponse;
import com.example.code.dto.TokenType;
import com.example.code.dto.UserDto;
import com.example.code.repositories.JwtTokenRepositoryImpl;
import com.example.code.services.AuthService;
import com.example.code.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenRepositoryImpl jwtTokenRepository;

    @Override
    public AuthenticationResponse login(UserDto dto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        User user = (User) userDetailsService.loadUserByUsername(dto.getUsername());
        String token = jwtService.generateToken(user);
        saveToken(token, user.getId());

        return AuthenticationResponse
                .builder()
                .id(user.getId())
                .token(token)
                .build();
    }


    private void saveToken(String token, Long userId) {
        jwtTokenRepository.deleteAllByUserId(userId);

        String key = userId + ":"
                + TokenType.ACCESS.toString().toLowerCase()
                + ":" + jwtService.extractId(token);

        jwtTokenRepository.save(key, token, jwtService.extractExpiration(token).getTime());
    }
}
