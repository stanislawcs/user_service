package com.example.code.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExceptionResponse extends RuntimeException{
    private String message;
}
