package org.sopt.diary.service;

import org.sopt.diary.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserPresent(Long id) {
        return userRepository.existsById(id);
    }
}
