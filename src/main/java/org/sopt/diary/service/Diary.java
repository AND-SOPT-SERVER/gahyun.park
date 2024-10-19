package org.sopt.diary.service;


import java.time.LocalDateTime;

public class Diary {
    private final String content;
    private final long id;
    private final String title;
    private final LocalDateTime date;

    public Diary(long id, String content, String title, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }
}
