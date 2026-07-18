package com.payflow.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @Email(message = "Invalid email")
    @Schema(
            description = "User Email",
            example = "kushwah@gmail.com"
    )
    private String email;

    @NotBlank(message = "password is required")
    @Schema(
            description = "User password",
            example = "123456"
    )
    private String password;
}
