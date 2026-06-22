package com.payflow.auth.service;

import com.payflow.auth.client.UserServiceClient;
import com.payflow.auth.client.WalletClient;
import com.payflow.auth.dto.*;
import com.payflow.auth.entity.User;
import com.payflow.auth.enums.Role;
import com.payflow.auth.exception.UserAlreadyExistsException;
import com.payflow.auth.repository.UserRepository;
import com.payflow.auth.security.JwtUtil;
import com.payflow.auth.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceClient userServiceClient;

    private final WalletClient walletClient;

    private final JwtUtil jwtUtil;

    @Override
    @CircuitBreaker(
            name = "walletService",
            fallbackMethod = "registerFallback"
    )
    @Transactional
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

        CreateWalletRequest request1 = CreateWalletRequest.builder()
                        .userId(user.getId())
                                .build();
        walletClient.createWallet(request1);

        userServiceClient.createUserProfile(UserProfileRequestDto.builder()
                .authUserId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build()
        );

        return "User register successfully";
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

       User user = userRepository.findByEmail(request.getEmail())
               .orElseThrow(()-> new RuntimeException("Invalid email or password"));

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!matches){
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = jwtUtil.generateToken(user.getId(),user.getEmail(),user.getRole().name());

        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return LoginResponseDto.builder()
                .email(user.getEmail())
                .token(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole().name())
                .build();

    }

    @Override
    public UserProfileResponseDto getProfile() {

        String email = SecurityUtil.getCurrentUser();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        return UserProfileResponseDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
    public String registerFallback(
            RegisterRequestDto request,
            Exception ex){
     throw new RuntimeException("Wallet Service is unavailable , Register failed.");
    }
}
