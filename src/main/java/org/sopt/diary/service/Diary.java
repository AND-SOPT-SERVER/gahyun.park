package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;

public class Diary {
    private final String content;
    private final long id;

    public Diary(long id, String content) {
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
