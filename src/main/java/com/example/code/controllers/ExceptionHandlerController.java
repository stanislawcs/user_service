package com.example.code.controllers;

import com.example.code.dto.ExceptionResponse;
import com.example.code.exceptions.ObjectAlreadyExistsException;
import com.example.code.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
        log.error("UserNotFoundException was thrown with message: {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException of type {} was thrown with message: {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {

        log.error("ValidationException was thrown with message: {}", e.getMessage());

        ExceptionResponse response = new ExceptionResponse();
        StringBuilder stringBuilder = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            stringBuilder.append(fieldName).append(": ").append(message).append(";");
            response.setMessage(stringBuilder.toString());
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("Exception of type: {} was thrown: {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleObjectAlreadyExistsException(ObjectAlreadyExistsException e){
        log.error("ObjectAlreadyExistsException was thrown with message: {}", e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

}
