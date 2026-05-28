package com.payflow.user.controller;

import com.payflow.user.entity.UserProfile;
import com.payflow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public void createUser(@RequestBody UserProfile request) {

        userRepository.save(request);
    }
}