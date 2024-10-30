package org.sopt.diary.api;

import org.sopt.diary.dto.*;
import org.sopt.diary.error.UnAuthorizedError;
import org.sopt.diary.repository.Category;
import org.sopt.diary.service.AuthenticationService;
import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.sopt.diary.util.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class DiaryController {
    private final DiaryService diaryService;
    private final AuthenticationService authenticationService;
    private final static int CONTENT_LENGTH = 30;

    public DiaryController(DiaryService diaryService, AuthenticationService authenticationService) {
        this.diaryService = diaryService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/diary")
    ResponseEntity<Response> post(@RequestBody DiaryRequest diaryRequest, @RequestHeader("id") Long id) {
        if (authenticationService.isUserPresent(id)) {
            Validator.validTitleLength(diaryRequest.title());
            Validator.validContentLength(diaryRequest.content());
            diaryService.createDiary(diaryRequest.content(), diaryRequest.title(), diaryRequest.category(), diaryRequest.isPrivate());
        } else {
            throw new UnAuthorizedError();
        }
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

    @GetMapping("/diary")
    ResponseEntity<Response> getDiary(@PathVariable long id) {
        try {
            Diary diary = diaryService.getDiary(id);
            return ResponseEntity.ok(new DiaryDetailResponse(diary.getId(), diary.getContent(), diary.getTitle(), "2023", diary.getCategory()));
        } catch (NoSuchElementException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(error.getMessage()));
        }
    }

    @DeleteMapping("/diary/{id}")
    ResponseEntity<Response> delete(@PathVariable long id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/diary/{id}")
    ResponseEntity<Response> patch(@PathVariable long id, @RequestBody DiaryRequest diaryRequest) {
        try {
            if (diaryRequest.content().length() > CONTENT_LENGTH) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("30글자를 초과했습니다."));
            }
            diaryService.updateDiary(id, diaryRequest.content());
        } catch (NoSuchElementException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(error.getMessage()));
        }
        return ResponseEntity.ok().build();
    }


}
