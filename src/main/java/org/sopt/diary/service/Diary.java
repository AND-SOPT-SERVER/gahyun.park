package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;

import java.time.LocalDate;

public class Diary {
    private final String content;
    private final long id;
    private final String title;
    private final LocalDate date;

    public Diary(long id, String content, String title, LocalDate date) {
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

    public LocalDate getDate() {
        return date;
    }
}
