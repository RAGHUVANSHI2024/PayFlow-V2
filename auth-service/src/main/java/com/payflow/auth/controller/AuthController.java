package com.payflow.auth.controller;

import com.payflow.auth.dto.*;
import com.payflow.auth.entity.User;
import com.payflow.auth.repository.UserRepository;
import com.payflow.auth.response.ApiResponse;
import com.payflow.auth.security.JwtUtil;
import com.payflow.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

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

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getProfile() {

        UserProfileResponseDto profile =
                authService.getProfile();

        ApiResponse<?> response = ApiResponse.builder()
                .message("Profile fetched successfully")
                .status(HttpStatus.OK.value())
                .data(profile)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refreshToken(@RequestBody RefreshTokenRequestDto request){
        String email = jwtUtil.extractEmail(request.getRefreshToken());

        User user  = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User  not found "));

        String accessToken = jwtUtil.generateToken(email, user.getRole().name());

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .token(accessToken)
                .refreshToken(request.getRefreshToken())
                .email(email)
                .build();

        ApiResponse response = ApiResponse.builder()
                .message("Access Token Refreshed")
                .status(HttpStatus.OK.value())
                .data(responseDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminEndpoint() {

        return ResponseEntity.ok(
                "Welcome Admin"
        );
    }
}
