package org.sopt.diary.dto;

import jakarta.validation.constraints.NotBlank;


public class SignUpRequest {

    @NotBlank(message = "로그인 아이디를 입력해주세요")
    private final String loginId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private final String password;

    @NotBlank(message = "닉네임을 입력해주세요")
    private final String nickname;

    public SignUpRequest(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;

    }

    public final String getLoginId() {
        return this.loginId;
    }

    public final String getPassword() {
        return this.password;
    }

    public final String getNickname() {
        return this.nickname;
    }
}