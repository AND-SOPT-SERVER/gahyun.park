package org.sopt.seminar1;


import org.sopt.seminar1.DiaryRepository;

import java.util.List;

// 처리 로직
public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    final void writeDiary(final String body) {
        //final Diary diary = new Diary(null, body, false);
        /* 질문 : 원래는  body 자체를 넘겨주는게 아닌 주석처럼 Diary 객체를 생성해서 diaryRepository 에 넘겨주었습니다
        diaryRepository에서는 body 정보만 가지고 storage에 저장하고 이후 조회할 때 Diary 객체를 생성하는 구조였습니다.
        하지만 저는 다음과 같은 이유로 body 매개변수만 save 메서드에 넘기도록 변경했습니다
        1. body 정보만 필요하기에 매개변수로 받은 body만 넘겨줘도 된다
        2. 복구 기능을 위해 storage에 Diary 객체 자체를 저장한 이후 조회할때 별도로 Diary 객체를 생성하지 않도록 구현했습니다.
           이때 위 주석처럼 Diary 객체를 넘겨주게 되면 id가 null로 출력이 됩니다.
        원래 세미나 코드처럼 diary 객체를 생성해서 save 메서드에게 넘겨주었을 때 생기는 이점이 있는지 궁금합니다
        * */
        diaryRepository.save(body);
    }

    final List<Diary> getDiaryList() {
        return diaryRepository.findAll();
    }

    final void deleteDiary(final long id) {
        diaryRepository.remove(id);
    }

    final void patchDiary(final long id, final String body) {
        diaryRepository.patch(id, body);
    }

    final void restoreDiary(final long id) {
        diaryRepository.restore(id);
    }

}