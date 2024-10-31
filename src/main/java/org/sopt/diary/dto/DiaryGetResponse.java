package org.sopt.diary.dto;

public record DiaryGetResponse(
        Long id,
        String title,
        String nickname,
        String createdAt
) {
}