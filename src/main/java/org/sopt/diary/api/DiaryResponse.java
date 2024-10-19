package org.sopt.diary.api;

public class DiaryResponse {
    private long id;
    private String content;

    public DiaryResponse(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
