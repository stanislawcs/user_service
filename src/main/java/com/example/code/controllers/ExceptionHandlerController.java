package com.example.code.controllers;

import com.example.code.dto.ExceptionResponse;
import com.example.code.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {

        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }



}
