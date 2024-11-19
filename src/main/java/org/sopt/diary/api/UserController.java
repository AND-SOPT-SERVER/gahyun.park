package org.sopt.diary.api;

import jakarta.validation.Valid;
import org.sopt.diary.dto.SignInRequest;
import org.sopt.diary.dto.SignInResponse;
import org.sopt.diary.dto.SignUpRequest;
import org.sopt.diary.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/signup")
    ResponseEntity<Void> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest.getLoginId(), signUpRequest.getPassword(), signUpRequest.getNickname());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/users/signin")
    ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        Long userId = userService.signIn(signInRequest.loginId(), signInRequest.password());
        return ResponseEntity.status(HttpStatus.OK).body(new SignInResponse(userId));
    }
}
