package com.payflow.auth.controller;

import com.payflow.auth.dto.LoginRequestDto;
import com.payflow.auth.dto.LoginResponseDto;
import com.payflow.auth.dto.RegisterRequestDto;
import com.payflow.auth.response.ApiResponse;
import com.payflow.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @Valid @RequestBody LoginRequestDto request
    ) {

        LoginResponseDto loginResponse =
                authService.login(request);

        ApiResponse<?> response = ApiResponse.builder()
                .message("Login successful")
                .status(HttpStatus.OK.value())
                .data(loginResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<String> profile() {

        return ResponseEntity.ok(
                "Protected profile endpoint accessed"
        );
    }
}
