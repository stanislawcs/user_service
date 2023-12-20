package com.example.code.dto;

import lombok.*;


@Data
@AllArgsConstructor
public class ListUserDTO {
    private Long id;
    private String username;
    private String email;
}
