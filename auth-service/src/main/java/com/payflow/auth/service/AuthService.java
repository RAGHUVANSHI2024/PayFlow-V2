package com.payflow.auth.service;

import com.payflow.auth.dto.RegisterRequestDto;

public interface AuthService {

    String register(RegisterRequestDto request);
}
