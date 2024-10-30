package org.sopt.diary.dto;

import org.sopt.diary.repository.Category;

public record DiaryUpdateRequest(String content, Category category) {
}
