package org.sopt.diary.dto;

import java.util.List;

public record DiaryListResponse(
        List<DiaryGetResponse> diaryList
) {

}