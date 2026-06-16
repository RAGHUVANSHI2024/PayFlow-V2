package com.payflow.user.controller;

import com.payflow.user.entity.UserProfile;
import com.payflow.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile request) {
        UserProfile user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }
}