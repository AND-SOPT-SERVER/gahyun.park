package org.sopt.diary.api;

import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

// RestController라는 annotation을 통해 DiaryController는 bean 객체가 됨
// DiaryController는 bean 객체가 됨으로써 new를 통해 직접 생성할 필요가 없고 Spring Boot에 의해 관리
// @RestController는 @Controller에 @ResponseBody가 추가, JSON 형태로 객체 데이터(Response entity) 반환
// Spring Boot의  DI/IoC:  객체를 사용자가 new 키워드를 통해 생성하고
// 소멸시키는 과정 필요 없이 의존성을 주입(DI)해 Spring 컨테이너가 Bean들의 생명 주기를 관리해주는 기능(Ioc)
// 이렇게 의존성 주입을 하는 이유는 코드의 결합도를 낮추고 유지 보수성을 높이기 위해
@RestController
public class DiaryController {
    private final DiaryService diaryService;
    private final static int CONTENT_LENGTH = 30;

    // 사용할 의존 객체를 생성자를 통해 주입 받음
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 클라이언트가 Post 요청을 할 때 호출
    // RequestBody annotation을 통해서 HTTP의 Body를 해당 어노테이션이 지정된 객체에 매핑
    // DiaryRequest는 Entity 필드들을 담아 로직을 처리하는 곳으로 데이터를 넘겨주고 DTO 클래스라함.
    @PostMapping("/diary")
    ResponseEntity<Response> post(@RequestBody DiaryRequest diaryRequest) {
        try {
            if (diaryRequest.content().length() > CONTENT_LENGTH) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("30글자를 초과했습니다."));
            }
            diaryService.createDiary(diaryRequest.content(), diaryRequest.title(), diaryRequest.category());
        } catch (DuplicateKeyException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(error.getMessage()));
        }
        // ResponseEntity.ok().build()는 성공했지만 특별한 데이터를 반환하지 않겠다.
        return ResponseEntity.ok().build();
    }

    // ResponseEntity는 HttpStatus, HttpHeaders, HttpBody를 포함한 클래스
    @GetMapping("/diaries")
    ResponseEntity<DiaryListResponse> getDiaries(@RequestParam(value = "category", required = false) String category) {
        // Service로부터 가져온 DiaryList
        List<Diary> diaryList;
        if (category != null) {
            diaryList = diaryService.getDiaryListByCategory(category);
        } else {
            diaryList = diaryService.getList();
        }

        // Client 와 협의한 interface 로 변화
        List<DiaryGetResponse> diaryResponseList = new ArrayList<>();
        for (Diary diary : diaryList) {
            diaryResponseList.add(new DiaryGetResponse(diary.getId(), diary.getContent()));
        }

        // DiaryListResponse를 JSON 형태로 반환
        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
    }

    @GetMapping("/diary")
    ResponseEntity<Response> getDiary(@PathVariable long id) {
        try {
            Diary diary = diaryService.getDiary(id);
            return ResponseEntity.ok(new DiaryDetailResponse(diary.getId(), diary.getContent(), diary.getTitle(), diary.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), diary.getCategory()));
        } catch (NoSuchElementException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(error.getMessage()));
        }
    }

    @DeleteMapping("/diary/{id}")
    ResponseEntity<Response> delete(@PathVariable long id) {
        try {
            diaryService.deleteDiary(id);
        } catch (NoSuchElementException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(error.getMessage()));
        }
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
