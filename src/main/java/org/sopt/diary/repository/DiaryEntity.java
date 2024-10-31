package org.sopt.diary.repository;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

// 데이터베이스 있는 것를 매핑시켜주는 것
@Entity
@Table(name = "diary")
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 채번의 역할 ( 알아서 넣어주는 역할 )
    public Long id;

    @Column(nullable = false)
    public String content;
    @Column(nullable = false, unique = true)
    public String title;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    public Timestamp createdAt;

    @Column(name = "is_private", nullable = false, columnDefinition = "TINYINT(1)")
    public Boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Category category;

    public DiaryEntity() {

    }

    public DiaryEntity(final String content, final String title, final Category category, final Boolean isPrivate, final UserEntity user) {
        this.content = content;
        this.title = title;
        this.category = category;
        this.isPrivate = isPrivate;
        this.user = user;
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

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public Category getCategory() {
        return this.category;
    }

    public Long getUserId() {
        return this.user.getId();
    }

    public String getUserNickname() {
        return this.user.getNickname();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
