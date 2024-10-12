package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;

public class Diary {
    private final String name;
    private final long id;

    public Diary(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
