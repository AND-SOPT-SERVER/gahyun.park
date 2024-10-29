package org.sopt.diary.dto;

import java.util.List;

public class DiaryListResponse implements Response {
    private List<DiaryGetResponse> diaryList;

    public DiaryListResponse(List<DiaryGetResponse> diaryList) {
        this.diaryList = diaryList;
    }

    public List<DiaryGetResponse> getDiaryList() {
        return diaryList;
    }
}
