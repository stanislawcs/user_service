package com.example.code.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class AuthenticationResponse {
    private Long id;
    private String token;
}

