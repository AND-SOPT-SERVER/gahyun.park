package org.sopt.diary.api;

import com.fasterxml.jackson.annotation.JsonInclude;

public class DiaryResponse implements Response {
    private final long id;
    private final String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String date;

    public DiaryResponse(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public DiaryResponse(long id, String content, String title, String date) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
