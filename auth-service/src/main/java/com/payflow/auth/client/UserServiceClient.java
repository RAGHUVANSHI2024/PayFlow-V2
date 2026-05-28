package com.payflow.auth.client;

import com.payflow.auth.dto.UserProfileRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @PostMapping("/api/v1/users")
    void createUserProfile(@RequestBody UserProfileRequestDto request);

}
