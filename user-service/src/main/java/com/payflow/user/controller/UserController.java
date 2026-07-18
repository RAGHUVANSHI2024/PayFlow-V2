package com.payflow.user.controller;

import com.payflow.user.entity.UserProfile;
import com.payflow.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(
        name = "User Management",
        description = "Operation related to user profile management"
)
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "Create User",
            description = "Creates a new user profile."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "User already exist")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserProfile> createUser(@Valid @RequestBody UserProfile request) {
        UserProfile user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}