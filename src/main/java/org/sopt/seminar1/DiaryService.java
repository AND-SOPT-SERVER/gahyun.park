package org.sopt.seminar1;


import org.sopt.seminar1.DiaryRepository;

import java.util.List;

// 처리 로직
public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    final void writeDiary(final String body) {
        final Diary diary = new Diary(null, body);
        // 질문 : body 자체를 넘겨주는게 아닌 diary 객체로 넘겨주었을때 장점은 무엇인지
        diaryRepository.save(diary);
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


}