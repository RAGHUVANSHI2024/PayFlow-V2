package com.payflow.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotBlank(message = "Full name is required")
    @Schema(example = "raghu")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Schema(example = "kushwah@gmail.com")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(example = "123456")
    private String password;
}