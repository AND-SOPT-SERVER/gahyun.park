package org.sopt.diary.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findByIsPrivateFalse();

    List<DiaryEntity> findByUser_Id(long id);

}
