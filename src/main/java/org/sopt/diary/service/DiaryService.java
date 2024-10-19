package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(String content, String title) {
        LocalDateTime currentDate = LocalDateTime.now();
        diaryRepository.save(
                new DiaryEntity(content, title, currentDate)
        );
    }

    public List<Diary> getList() {
        Pageable pageable = PageRequest.of(0, 10);
        // repository로 부터 DiaryEntity 가져옴
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAllByOrderByDateDesc(pageable);

        // DiaryEntity를 Diary로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(new Diary(diaryEntity.getId(), diaryEntity.getContent(), diaryEntity.getTitle(), diaryEntity.getDate()));
        }

        return diaryList;
    }

    public Diary getDiary(long id) {
        // repository로 부터 DiaryEntity 가져옴
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAll();

        // DiaryEntity를 Diary로 변환해주는 작업
        for (DiaryEntity diaryEntity : diaryEntityList) {
            if (id == diaryEntity.getId())
                return new Diary(diaryEntity.getId(), diaryEntity.getContent(), diaryEntity.getTitle(), diaryEntity.getDate());
        }

        throw new NoSuchElementException(id + " not found");
    }
}
