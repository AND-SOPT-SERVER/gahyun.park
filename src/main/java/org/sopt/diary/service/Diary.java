package org.sopt.diary.service;


import org.sopt.diary.repository.Category;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Diary {
    private final String content;
    private final long id;
    private final String title;
    private final Timestamp date;
    private final Category category;

    public Diary(long id, String content, String title, Timestamp date, Category category) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.date = date;
        this.category = category;
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

    public Timestamp getDate() {
        return date;
    }

    public Category getCategory() {
        return this.category;
    }
}
