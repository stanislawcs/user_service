package com.example.code.controllers;

import com.example.code.dto.AuthenticationResponse;
import com.example.code.dto.UserDto;
import com.example.code.services.AuthService;
import com.example.code.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid UserDto dto) {
        return new ResponseEntity<>(userService.create(dto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid UserDto dto) {
        return new ResponseEntity<>(authService.login(dto), HttpStatus.OK);
    }

}
