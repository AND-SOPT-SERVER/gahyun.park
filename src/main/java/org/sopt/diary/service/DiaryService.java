package org.sopt.diary.service;

import jakarta.transaction.Transactional;
import org.sopt.diary.error.BadRequestException;
import org.sopt.diary.error.UnAuthorizedError;
import org.sopt.diary.repository.*;
import org.sopt.diary.util.ErrorMessages;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public DiaryService(DiaryRepository diaryRepository, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    public void createDiary(String content, String title, Category category, Boolean isPrivate, long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UnAuthorizedError());
        diaryRepository.save(new DiaryEntity(content, title, category, isPrivate, user));

    }


    public void deleteDiary(long id) {
        userRepository.findById(id).orElseThrow(() -> new UnAuthorizedError());
        diaryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NON_EXISTENT_DIARY));
        diaryRepository.deleteById(id);
    }

    // 데이터 베이스에 작업이 일어나도록 Transactional annotation 추가
    @Transactional
    public void updateDiary(long id, String content) {
        userRepository.findById(id).orElseThrow(() -> new UnAuthorizedError());
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NON_EXISTENT_DIARY));
        diaryEntity.setContent(content);
    }

    public List<Diary> getList() {
        // repository로 부터 DiaryEntity 가져옴
        final List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByOrderByCreatedAtDesc();

        // DiaryEntity를 Diary로 변환해주는 작업
        final List<Diary> diaryList = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(new Diary(diaryEntity.getId(), diaryEntity.getContent(), diaryEntity.getTitle(), diaryEntity.getCreatedAt(), diaryEntity.getCategory()));
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
                return new Diary(diaryEntity.getId(), diaryEntity.getContent(), diaryEntity.getTitle(), diaryEntity.getCreatedAt(), diaryEntity.getCategory());
        }

        throw new NoSuchElementException(id + " not found");
    }

    public List<Diary> getDiaryListByCategory(Category category) {
        final List<DiaryEntity> diaryEntityList = diaryRepository.findByCategory(category);

        return diaryEntityList.stream()
                .map(diaryEntity -> new Diary(
                        diaryEntity.getId(),
                        diaryEntity.getContent(),
                        diaryEntity.getTitle(),
                        diaryEntity.getCreatedAt(),
                        diaryEntity.getCategory()
                ))
                .toList();
    }
}
