package org.sopt.diary.dto;

import org.sopt.diary.repository.Category;

public record DiaryRequest(String content, String title, Category category, boolean isPrivate) {
}
