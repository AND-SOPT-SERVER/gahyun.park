package org.sopt.diary.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findTop10ByOrderByDateDesc();

    List<DiaryEntity> findByCategory(String category);

    // Optional 은 null이 올 수 있는 값을 감싸는 Wrapper 클래스
    Optional<DiaryEntity> findByTitle(String title);
}
