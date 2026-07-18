package com.payflow.auth.controller;

import com.payflow.auth.dto.*;
import com.payflow.auth.entity.User;
import com.payflow.auth.repository.UserRepository;
import com.payflow.auth.response.ApiResponse;
import com.payflow.auth.security.JwtUtil;
import com.payflow.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication",
        description = "User authentication and JWT management APIs"
)
public class AuthController {

    private final AuthService authService;
    
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @PostMapping("/register")
    @Operation(
            summary = "Registration User",
            description = "Create a new user account."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User registered successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Email already exists")
    })
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
    @Operation(
            summary = "Login",
            description = "Authenticate user and generate JWT Access Token and Refresh Token."

    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successful"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed")
    })
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
    @Operation(
            summary = "Protected Endpoint",
            description = "Only authentication user can access this endpoint."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Access granted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<String> profile() {

        return ResponseEntity.ok(
                "Protected profile endpoint accessed"
        );
    }

    @GetMapping("/me")
    @Operation(
            summary = "Current User Profile",
            description = "Return details of the currently authenticated user."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile fetched"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
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
    @Operation(
            summary = "Refresh Access Token",
            description = "Generate a new Access Token using a valid Refresh Token."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Access token refreshed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid refresh token")
    })
    public ResponseEntity<ApiResponse<?>> refreshToken(@RequestBody RefreshTokenRequestDto request){
        String email = jwtUtil.extractEmail(request.getRefreshToken());

        User user  = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User  not found "));

        String accessToken = jwtUtil.generateToken(user.getId(),email, user.getRole().name());

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
    @Operation(
            summary = "Admin Endpoint",
            description = "Accessible only to ADMIN users."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Access granted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<String> adminEndpoint() {

        return ResponseEntity.ok(
                "Welcome Admin"
        );
    }
}
