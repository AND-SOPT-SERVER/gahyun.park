package org.sopt.diary.repository;


import jakarta.persistence.*;

import java.time.LocalDateTime;

// 데이터베이스 있는 것를 매핑시켜주는 것
@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String content;
    @Column
    public String title;
    @Column
    public LocalDateTime date;
    @Column
    public String category;

    public DiaryEntity() {

    }


    public DiaryEntity(final String content, final String title, final LocalDateTime date, final String category) {
        this.content = content;
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public String getCategory() {
        return this.category;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
