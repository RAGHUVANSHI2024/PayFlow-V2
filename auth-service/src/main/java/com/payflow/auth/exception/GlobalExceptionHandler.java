package com.payflow.auth.exception;

import com.payflow.auth.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleUserAlreadyExists(UserAlreadyExistsException ex){

        ApiResponse<?> response = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .build();

        return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
    }
}
