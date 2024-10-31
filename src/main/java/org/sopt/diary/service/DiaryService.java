package org.sopt.diary.service;

import jakarta.transaction.Transactional;
import org.sopt.diary.dto.DiaryGetResponse;
import org.sopt.diary.dto.DiaryListResponse;
import org.sopt.diary.error.BadRequestException;
import org.sopt.diary.error.ForBiddenException;
import org.sopt.diary.error.UnAuthorizedError;
import org.sopt.diary.repository.*;
import org.sopt.diary.util.ErrorMessages;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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


    public void deleteDiary(long id, long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UnAuthorizedError());
        System.out.println(diaryRepository.findById(id).isPresent());
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NON_EXISTENT_DIARY));
        if (diaryEntity.getUserId() == userId) {
            diaryRepository.deleteById(id);
        } else {
            throw new ForBiddenException(ErrorMessages.FORBIDDEN_ERROR);
        }

    }

    @Transactional
    public void updateDiary(long id, String content, Category category, long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UnAuthorizedError());
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NON_EXISTENT_DIARY));
        if (diaryEntity.getUserId() == userId) {
            diaryEntity.setContent(content);
            diaryEntity.setCategory(category);
        } else {
            throw new ForBiddenException(ErrorMessages.FORBIDDEN_ERROR);
        }

    }

    public List<DiaryGetResponse> getList(String category, final String sort) {
        if (!Category.isPresent(category) && !category.equals("ALL")) {
            System.out.println("적합하지 않ㅇ,ㅁ");
            return new ArrayList<>(); // category가 적합하지 않으면 빈배열로 반환
        }

        List<DiaryGetResponse> diaryEntities = diaryRepository.findByIsPrivateFalse()
                .stream()
                .filter(diary -> diary.getCategory().name().equalsIgnoreCase(category)) // category와 일치하는 항목만 남기기
                .sorted((diary1, diary2) -> {
                    if ("LATEST".equals(sort)) {
                        return diary2.getCreatedAt().compareTo(diary1.getCreatedAt()); // 최신순 정렬
                    } else if ("QUANTITY".equals(sort)) {
                        return Integer.compare(diary2.getContent().length(), diary1.getContent().length()); // content 길이 순 정렬
                    }
                    return 0; // 정렬하지 않음
                })
                .limit(10)
                .map(diaryEntity -> new DiaryGetResponse(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getUserNickname(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diaryEntity.getCreatedAt())))
                .toList();
        System.out.println(diaryEntities);
        return diaryEntities;
    }

    public Diary getDiary(long id) {
        DiaryEntity diaryEntity = diaryRepository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.NON_EXISTENT_DIARY));
        return new Diary(diaryEntity.getId(), diaryEntity.getContent(), diaryEntity.getTitle(), diaryEntity.getCreatedAt(), diaryEntity.category);
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
