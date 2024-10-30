package org.sopt.diary.service;

import org.sopt.diary.error.BadRequestException;
import org.sopt.diary.repository.UserEntity;
import org.sopt.diary.repository.UserRepository;
import org.sopt.diary.util.ErrorMessages;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public final void signUp(final String loginId, final String password, final String nickname) {
        userRepository.save(new UserEntity(loginId, password, nickname));
    }

    public final Long signIn(final String loginId, final String password) {
        Optional<UserEntity> user = userRepository.findByLoginId(loginId);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get().getId();
        } else {
            throw new BadRequestException(ErrorMessages.INVALID_LOGIN_ID_OR_PASSWORD);
        }
    }
}
