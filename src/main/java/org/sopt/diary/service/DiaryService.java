package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(String content, String title) {
        LocalDate currentDate = LocalDate.now();
        diaryRepository.save(
                new DiaryEntity(content, title, currentDate)
        );
    }

    public List<Diary> getList() {
        // repository로 부터 DiaryEntity 가져옴
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAll();

        // DiaryEntity를 Diary로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(new Diary(diaryEntity.getId(), diaryEntity.getContent()));
        }

        return diaryList;
    }
}
