package org.sopt.diary.api;

import org.sopt.diary.dto.*;
import org.sopt.diary.repository.Category;
import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.sopt.diary.util.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class DiaryController {
    private final DiaryService diaryService;
    private final static int CONTENT_LENGTH = 30;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/diary")
    ResponseEntity<Response> post(@RequestBody DiaryRequest diaryRequest, @RequestHeader("id") Long id) {
        Validator.validTitleLength(diaryRequest.title());
        Validator.validContentLength(diaryRequest.content());
        diaryService.createDiary(diaryRequest.content(), diaryRequest.title(), diaryRequest.category(), diaryRequest.isPrivate(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/diaries")
    ResponseEntity<DiaryListResponse> getDiaries(@RequestParam(value = "category", required = false) Category category) {
        // Service로부터 가져온 DiaryList
        List<Diary> diaryList;
        if (category != null) {
            diaryList = diaryService.getDiaryListByCategory(category);
        } else {
            diaryList = diaryService.getList();
        }

        List<DiaryGetResponse> diaryResponseList = new ArrayList<>();
        for (Diary diary : diaryList) {
            diaryResponseList.add(new DiaryGetResponse(diary.getId(), diary.getContent()));
        }

        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
    }

    @GetMapping("/diary/{id}")
    ResponseEntity<Response> getDiary(@PathVariable long id) {
        Diary diary = diaryService.getDiary(id);
        return ResponseEntity.ok(new DiaryDetailResponse(diary.getId(), diary.getContent(), diary.getTitle(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diary.getCreatedAt()), diary.getCategory()));
    }

    @DeleteMapping("/diary/{id}")
    ResponseEntity<Response> delete(@PathVariable long id, @RequestHeader("id") Long userId) {
        System.out.println(userId);
        diaryService.deleteDiary(id, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/diary/{id}")
    ResponseEntity<Response> patch(@PathVariable long id, @RequestBody DiaryUpdateRequest diaryUpdateRequest, @RequestHeader("id") Long userId) {
        Validator.validContentLength(diaryUpdateRequest.content());
        diaryService.updateDiary(id, diaryUpdateRequest.content(), diaryUpdateRequest.category(), userId);
        return ResponseEntity.ok().build();
    }


}
