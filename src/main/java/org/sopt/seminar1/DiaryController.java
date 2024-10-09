package org.sopt.seminar1;


import java.util.List;

// service에서 처리된걸 controller에서 main으로 보냄
public class DiaryController {
    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();

    Status getStatus() {
        return status;
    }

    void boot() {
        this.status = Status.RUNNING;
    }

    void finish() {
        this.status = Status.FINISHED;
    }

    // APIS
    final List<Diary> getList() {
        return diaryService.getDiaryList();
    }

    final void post(final String body) {
        // 원래 사용자에서 온 값이 적절한 값인지 처리하는 역할도 함
        // body 30자보다 크면 예외처리
        if (body.length() >= 30) {
            throw new IllegalArgumentException("한 줄 일기는 30자 이상이어야 합니다.");
        }
        // IllegalArgumentException 는 매개변수가 잘못되었을 때 발생하는 예외로 사용자가 입력한 값이 잘못된 상황이기에 사용함
        diaryService.writeDiary(body);
    }

    final void delete(final String id) {
        if (!id.trim().matches("\\d+")) {
            throw new IllegalArgumentException("ID는 숫자만 포함해야 합니다.");
        }
        ;
        diaryService.deleteDiary(Long.parseLong(id));
    }

    final void patch(final String id, final String body) {
        if (!id.trim().matches("\\d+")) {
            throw new IllegalArgumentException("ID는 숫자만 포함해야 합니다.");
        }
        ;
        if (body.length() >= 30) {
            throw new IllegalArgumentException("한 줄 일기는 30자 이상이어야 합니다.");
        }
        diaryService.patchDiary(Long.parseLong(id), body);
    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}