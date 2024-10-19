package org.sopt.diary.api;

import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// RestController라는 annotation을 통해 DiaryController는 bean 객체가 됨
// DiaryController는 bean 객체가 됨으로써 new를 통해 직접 생성할 필요가 없고 Spring Boot에 의해 관리
// @RestController는 @Controller에 @ResponseBody가 추가, JSON 형태로 객체 데이터(Response entity) 반환
// Spring Boot의  DI/IoC:  객체를 사용자가 new 키워드를 통해 생성하고
// 소멸시키는 과정 필요 없이 의존성을 주입(DI)해 Spring 컨테이너가 Bean들의 생명 주기를 관리해주는 기능(Ioc)
// 이렇게 의존성 주입을 하는 이유는 코드의 결합도를 낮추고 유지 보수성을 높이기 위해
@RestController
public class DiaryController {
    private final DiaryService diaryService;

    // 사용할 의존 객체를 생성자를 통해 주입 받음
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 클라이언트가 Post 요청을 할 때 호출
    // RequestBody annotation을 통해서 HTTP의 Body를 해당 어노테이션이 지정된 객체에 매핑
    // DiaryRequest는 Entity 필드들을 담아 로직을 처리하는 곳으로 데이터를 넘겨주고 DTO 클래스라함.
    @PostMapping("/diary")
    void post(@RequestBody DiaryRequest diaryRequest) {
        diaryService.createDiary(diaryRequest.getContent(), diaryRequest.getTitle());
    }

    // ResponseEntity는 HttpStatus, HttpHeaders, HttpBody를 포함한 클래스
    @GetMapping("/diary")
    ResponseEntity<DiaryListResponse> get() {
        // Service로부터 가져온 DiaryList
        List<Diary> diaryList = diaryService.getList();

        // Client 와 협의한 interface 로 변화
        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for (Diary diary : diaryList) {
            diaryResponseList.add(new DiaryResponse(diary.getId(), diary.getContent()));
        }

        // DiaryListResponse를 JSON 형태로 반환
        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
    }
}
