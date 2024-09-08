package com.example.endpointmonitoring.service;

import com.example.endpointmonitoring.model.User;
import com.example.endpointmonitoring.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateUser(String accessToken) {
        return userRepository.findByAccessToken(accessToken)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials")
                );
    }
}
