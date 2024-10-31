package org.sopt.diary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.diary.repository.Category;

public record DiaryRequest(@NotBlank(message = "일기 본문을 입력해주세요")
                           @Size(min = 1, max = 30, message = "본문은 1자~30자로 입력해주세요")
                           String content,
                           @NotBlank(message = "일기 제목을 입력해주세요")
                           @Size(min = 1, max = 10, message = "제목은 1자~10자로 입력해주세요") String title,
                           @NotNull(message = "카테고리를 입력해주세요") Category category,
                           @NotNull(message = "공개 여부를 입력해주세요") boolean isPrivate) {

    public DiaryRequest(String content, String title, Category category, boolean isPrivate) {
        this.content = content;
        this.title = title;
        this.isPrivate = isPrivate;
        this.category = category;
    }
}
