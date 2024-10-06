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
        diaryService.writeDiary(body);
    }

    final void delete(final String id) {

    }

    final void patch(final String id, final String body) {

    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}