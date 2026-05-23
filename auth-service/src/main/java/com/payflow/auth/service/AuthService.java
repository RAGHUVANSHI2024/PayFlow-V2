package com.payflow.auth.service;

import com.payflow.auth.dto.LoginRequestDto;
import com.payflow.auth.dto.LoginResponseDto;
import com.payflow.auth.dto.RegisterRequestDto;

public interface AuthService {

    String register(RegisterRequestDto request);

    LoginResponseDto login(LoginRequestDto request);
}
