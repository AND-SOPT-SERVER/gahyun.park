package org.sopt.diary.service;

import jakarta.transaction.Transactional;
import org.sopt.diary.dto.DiaryGetResponse;
import org.sopt.diary.error.BadRequestException;
import org.sopt.diary.error.ForBiddenException;
import org.sopt.diary.error.UnAuthorizedError;
import org.sopt.diary.repository.*;
import org.sopt.diary.util.ErrorMessages;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public DiaryService(DiaryRepository diaryRepository, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    public final void createDiary(String content, String title, Category category, Boolean isPrivate, long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UnAuthorizedError());
        diaryRepository.save(new DiaryEntity(content, title, category, isPrivate, user));

    }


    public final void deleteDiary(long id, long userId) {
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

    public final List<DiaryGetResponse> getList(final String category, final String sort) {
        if (!Category.isPresent(category) && !category.equals("ALL")) {
            return new ArrayList<>(); // category가 적합하지 않으면 빈배열로 반환
        }
        if (category.equals("ALL")) return diaryRepository.findByIsPrivateFalse()
                .stream()
                .sorted((diary1, diary2) -> sortDiary(diary1, diary2, sort))
                .limit(10)
                .map(diaryEntity -> new DiaryGetResponse(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getUserNickname(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diaryEntity.getCreatedAt())))
                .toList();
        return diaryRepository.findByIsPrivateFalse()
                .stream()
                .filter(diary -> diary.getCategory().name().equalsIgnoreCase(category)) // category와 일치하는 항목만 남기기
                .sorted((diary1, diary2) -> sortDiary(diary1, diary2, sort))
                .limit(10)
                .map(diaryEntity -> new DiaryGetResponse(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getUserNickname(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diaryEntity.getCreatedAt())))
                .toList();
    }

    public final List<DiaryGetResponse> getMyList(final String category, final String sort, long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UnAuthorizedError());
        if (!Category.isPresent(category) && !category.equals("ALL")) {
            return new ArrayList<>();
        }
        return diaryRepository.findByUser_Id(userId)
                .stream()
                .filter(diary -> diary.getCategory().name().equalsIgnoreCase(category))
                .sorted((diary1, diary2) -> sortDiary(diary1, diary2, sort))
                .limit(10)
                .map(diaryEntity -> new DiaryGetResponse(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getUserNickname(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diaryEntity.getCreatedAt())))
                .toList();
    }

    public final Diary getDiary(long id, long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UnAuthorizedError());
        DiaryEntity diaryEntity = diaryRepository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.NON_EXISTENT_DIARY));
        if (!diaryEntity.getUserId().equals(userId) && diaryEntity.getIsPrivate()) {
            throw new ForBiddenException(ErrorMessages.FORBIDDEN_ERROR);
        }// 비공개고 일기 주인이 아니므로 응답 X
        return new Diary(diaryEntity.getId(), diaryEntity.getContent(), diaryEntity.getTitle(), diaryEntity.getCreatedAt(), diaryEntity.category);
    }

    private int sortDiary(DiaryEntity diary1, DiaryEntity diary2, String sort) {
        if ("LATEST".equals(sort)) {
            return diary2.getCreatedAt().compareTo(diary1.getCreatedAt());
        } else if ("QUANTITY".equals(sort)) {
            return Integer.compare(diary2.getContent().length(), diary1.getContent().length());
        }
        return 0;
    }

}
