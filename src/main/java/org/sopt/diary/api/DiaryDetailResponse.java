package org.sopt.diary.api;

public record DiaryDetailResponse(long id, String content, String title, String date) implements Response {
}
