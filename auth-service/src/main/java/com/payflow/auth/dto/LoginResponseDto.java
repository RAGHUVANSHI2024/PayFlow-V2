package com.payflow.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    @Schema(description = "JWT Access Token")
    private String token;
    @Schema(description = "User Email")
    private String email;
    @Schema(description = "User Role")
    private String role;
    @Schema(description = "JWT Refresh Token")
    private String refreshToken;
}
