package org.sopt.diary.service;

import jakarta.transaction.Transactional;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.dao.DuplicateKeyException;
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

    public void createDiary(String content, String title, String category) {
        LocalDateTime currentDate = LocalDateTime.now();
        if (diaryRepository.findByTitle(title).isPresent())
            throw new DuplicateKeyException("이미 존재하는 제목입니다");

        diaryRepository.save(
                new DiaryEntity(content, title, currentDate, category)
        );
    }

    private void checkExistingId(long id) {
        if (!diaryRepository.existsById(id)) throw new NoSuchElementException("존재하지 않는 일기입니다");
    }

    public void deleteDiary(long id) {
        checkExistingId(id);
        diaryRepository.deleteById(id);
    }

    // 데이터 베이스에 작업이 일어나도록 Transactional annotation 추가
    @Transactional
    public void updateDiary(long id, String content) {
        checkExistingId(id);
        DiaryEntity diaryEntity = diaryRepository.findById(id).get();
        diaryEntity.setContent(content);
    }

    public List<Diary> getList() {
        // repository로 부터 DiaryEntity 가져옴
        final List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByOrderByDateDesc();

        // DiaryEntity를 Diary로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(new Diary(diaryEntity.getId(), diaryEntity.getContent(), diaryEntity.getTitle(), diaryEntity.getDate(), diaryEntity.getCategory()));
        }

        return diaryList;
    }

    public Diary getDiary(long id) {
        // repository로 부터 DiaryEntity 가져옴
        // findById 사용해도 될듯 ?
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAll();

        // DiaryEntity를 Diary로 변환해주는 작업
        for (DiaryEntity diaryEntity : diaryEntityList) {
            if (id == diaryEntity.getId())
                return new Diary(diaryEntity.getId(), diaryEntity.getContent(), diaryEntity.getTitle(), diaryEntity.getDate(), diaryEntity.getCategory());
        }

        throw new NoSuchElementException(id + " not found");
    }

    public List<Diary> getDiaryListByCategory(String category) {
        final List<DiaryEntity> diaryEntityList = diaryRepository.findByCategory(category);

        final List<Diary> diaryList = new ArrayList<>();
        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(new Diary(diaryEntity.getId(), diaryEntity.getContent(), diaryEntity.getTitle(), diaryEntity.getDate(), diaryEntity.getCategory()));
        }

        return diaryList;
    }
}
