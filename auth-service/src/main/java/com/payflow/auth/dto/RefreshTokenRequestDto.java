package com.payflow.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RefreshTokenRequestDto {

    @Schema(description = "Refresh JWT Token")
    private String refreshToken;

}
