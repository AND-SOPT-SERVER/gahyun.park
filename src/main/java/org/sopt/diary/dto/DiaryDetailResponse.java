package org.sopt.diary.dto;

public record DiaryDetailResponse(long id, String content, String title, String date,
                                  String category) implements Response {
}
