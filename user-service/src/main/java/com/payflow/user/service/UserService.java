package com.payflow.user.service;

import com.payflow.user.entity.UserProfile;
import com.payflow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProfile createUser(UserProfile profile){

        UserProfile userProfile = userRepository.save(profile);

        return userProfile;
    }
}
