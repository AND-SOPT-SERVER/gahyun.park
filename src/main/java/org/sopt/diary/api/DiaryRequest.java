package org.sopt.diary.api;

public class DiaryRequest {
    private final String content;
    private final String title;

    public DiaryRequest(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
