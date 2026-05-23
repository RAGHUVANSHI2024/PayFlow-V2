package com.payflow.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
