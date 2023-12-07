package com.example.code.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsernameAlreadyTaken extends RuntimeException {
    public UsernameAlreadyTaken(String message) {
        super(message);
    }

}
