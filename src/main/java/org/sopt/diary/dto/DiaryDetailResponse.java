package org.sopt.diary.dto;

import org.sopt.diary.repository.Category;

public record DiaryDetailResponse(long id, String content, String title, String date,
                                  Category category) implements Response {
}
