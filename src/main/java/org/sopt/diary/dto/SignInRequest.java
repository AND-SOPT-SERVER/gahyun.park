package org.sopt.diary.dto;

import jakarta.validation.constraints.NotBlank;


public record SignInRequest(@NotBlank(message = "로그인 아이디를 입력해주세요") String loginId,
                            @NotBlank(message = "비밀번호를 입력해주세요") String password) {

    public SignInRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;

    }

    @Override
    public final String loginId() {
        return this.loginId;
    }

    @Override
    public final String password() {
        return this.password;
    }

}