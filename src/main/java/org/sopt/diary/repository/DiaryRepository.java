package org.sopt.diary.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findTop10ByOrderByCreatedAtDesc();

    List<DiaryEntity> findByCategory(Category category);

    // Optional 은 null이 올 수 있는 값을 감싸는 Wrapper 클래스
    Optional<DiaryEntity> findByTitle(String title);

}
