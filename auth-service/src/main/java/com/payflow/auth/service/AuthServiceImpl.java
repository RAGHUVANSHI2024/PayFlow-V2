package com.payflow.auth.service;

import com.payflow.auth.dto.RegisterRequestDto;
import com.payflow.auth.entity.User;
import com.payflow.auth.enums.Role;
import com.payflow.auth.exception.UserAlreadyExistsException;
import com.payflow.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("Email Already Exist !");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User register successfully";
    }
}
