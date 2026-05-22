package com.payflow.auth.controller;

import com.payflow.auth.dto.RegisterRequestDto;
import com.payflow.auth.response.ApiResponse;
import com.payflow.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterRequestDto request){

        String register = authService.register(request);

        ApiResponse<?> response = ApiResponse.builder()
                .message(register)
                .status(HttpStatus.CREATED.value())
                .data(null)
                .build();

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
