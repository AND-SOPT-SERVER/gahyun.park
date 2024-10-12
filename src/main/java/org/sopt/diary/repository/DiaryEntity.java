package org.sopt.diary.repository;


import jakarta.persistence.*;

// 데이터베이스 있는 것를 매핑시켜주는 것
@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String name;

    public DiaryEntity() {

    }


    public DiaryEntity(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return this.id;
    }
}
