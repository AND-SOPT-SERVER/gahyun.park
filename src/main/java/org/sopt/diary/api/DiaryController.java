package org.sopt.diary.api;

import jakarta.validation.Valid;
import org.sopt.diary.dto.*;
import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/diary")
    ResponseEntity<Void> post(@Valid @RequestBody DiaryRequest diaryRequest, @RequestHeader("id") Long id) {
        diaryService.createDiary(diaryRequest.content(), diaryRequest.title(), diaryRequest.category(), diaryRequest.isPrivate(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/diaries")
    ResponseEntity<List<DiaryGetResponse>> getDiaries(@RequestParam(value = "category", required = false, defaultValue = "ALL") String category, @RequestParam(value = "sort", defaultValue = "LATEST", required = false) String sort) {
        List<DiaryGetResponse> Diaries = diaryService.getList(category, sort);
        return ResponseEntity.ok(Diaries);

    }

    @GetMapping("/diaries/my")
    ResponseEntity<List<DiaryGetResponse>> getMyDiaries(@RequestParam(value = "category", required = false, defaultValue = "ALL") String category, @RequestParam(value = "sort", defaultValue = "LATEST", required = false) String sort, @RequestHeader("id") Long userId) {
        List<DiaryGetResponse> Diaries = diaryService.getMyList(category, sort, userId);
        return ResponseEntity.ok(Diaries);
    }


    @GetMapping("/diary/{id}")
    ResponseEntity<DiaryDetailResponse> getDiary(@PathVariable long id, @RequestHeader("id") Long userId) {
        Diary diary = diaryService.getDiary(id, userId);
        return ResponseEntity.ok(new DiaryDetailResponse(diary.getId(), diary.getContent(), diary.getTitle(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diary.getCreatedAt()), diary.getCategory()));
    }

    @DeleteMapping("/diary/{id}")
    ResponseEntity<Void> delete(@PathVariable long id, @RequestHeader("id") Long userId) {
        diaryService.deleteDiary(id, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/diary/{id}")
    ResponseEntity<Void> patch(@PathVariable long id, @Valid @RequestBody DiaryUpdateRequest diaryUpdateRequest, @RequestHeader("id") Long userId) {
        diaryService.updateDiary(id, diaryUpdateRequest.content(), diaryUpdateRequest.category(), userId);
        return ResponseEntity.ok().build();
    }


}
