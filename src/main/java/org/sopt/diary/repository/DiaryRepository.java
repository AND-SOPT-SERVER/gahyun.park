package org.sopt.diary.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    @Query("SELECT diary FROM DiaryEntity diary WHERE diary.isPrivate = false AND (:category = 'ALL' OR diary.category = :category) ORDER BY diary.createdAt DESC")
    List<DiaryEntity> findTop10ByIsPrivateFalseAndCategoryOrderByCreatedAtDesc(@Param("category") String category, Pageable pageable);

    @Query("SELECT diary FROM DiaryEntity diary WHERE diary.isPrivate = false AND (:category = 'ALL' OR diary.category = :category)  ORDER BY LENGTH(diary.content) DESC")
    List<DiaryEntity> findTop10ByIsPrivateFalseAndCategoryOrderByContentLengthDesc(@Param("category") String category, Pageable pageable);


    @Query("SELECT diary FROM DiaryEntity diary WHERE diary.isPrivate = false AND (:category = 'ALL' OR diary.category = :category)")
    List<DiaryEntity> findTop10ByIsPrivateFalseAndCategory(@Param("category") String category, Pageable pageable);

    List<DiaryEntity> findByIsPrivateFalse();

    List<DiaryEntity> findByCategory(Category category);

    // Optional 은 null이 올 수 있는 값을 감싸는 Wrapper 클래스
    Optional<DiaryEntity> findByTitle(String title);

}
