package org.sopt.diary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.diary.repository.Category;

public record DiaryUpdateRequest(
        @NotBlank(message = "일기 본문을 입력해주세요")
        @Size(min = 1, max = 30, message = "본문은 1자~30자로 입력해주세요")
        String content,
        @NotNull(message = "카테고리를 입력해주세요") Category category) {
}
