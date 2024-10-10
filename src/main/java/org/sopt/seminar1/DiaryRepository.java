package org.sopt.seminar1;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// 저장소
public class DiaryRepository {
    private final Map<Long, Diary> storage = new ConcurrentHashMap<>();
    /*
    map은 key value 형태로 저장 ( 리스트나 배열처럼 순차적으로 요소 값을 구하지 않음 )
    HashMap은 빠르지만 thread-safe하지 않음 반면에 ConcurrentHashMap은 thread-safe해 멀티 스레드 환경에서 사용하기 좋음
    */
    private final AtomicLong numbering = new AtomicLong();
    /* Long 자료형을 Wrapping 클래스로 감싼 자료형
    일반적으로 Long 자료형을 사용하면 멀티쓰레드 환경에서 동기화 문제 때문에 synchronized 키워드를 사용해서 값을 유지시켜야함
    하지만 AtomicLong은 내부에 CAS 알고리즘을 사용해서 synchronized 보다 적은 비용으로 동시성을 보장할 수 있게함
     */

    final void save(final String body) {
        // 채번 과정
        final long id = numbering.addAndGet(1);
        // addAndGet 메서드는 AtomicLong의 현재 값을 증가시키고 그 값을 long 타입으로 반환

        // 저장 과정
        final Diary diary = new Diary(id, body, false);
        storage.put(id, diary);
    }

    final void remove(final long id) {
        checkContainId(id);
        Diary diary = storage.get(id);
        diary.setDeleted(true);

    }

    final List<Diary> findAll() {
        // 1. diary를 담을 구조
        final List<Diary> diaryList = new ArrayList<>();

        // 2. 저장한 값을 불러오는 반복구조
        for (long index = 1; index <= numbering.longValue(); index++) {
            final Diary diary = storage.get(index);

            // 2-1. 불러온 값을 구성한 자료구조로 이관, 삭제된 요소면 diary로 만들지 않음.
            if (!diary.isDeleted()) {
                diaryList.add(diary);
            }
        }
        // 3. 불러온 자료구조를 응답
        return diaryList;
    }

    final void patch(final long id, final String body) {
        checkContainId(id);
        Diary diary = storage.get(id);
        diary.setBody(body);
    }

    final void restore(final long id) {
        checkContainId(id);
        Diary diary = storage.get(id);
        if (!diary.isDeleted()) throw new IllegalArgumentException("삭제된 일기가 아닙니다");
        diary.setDeleted(false);
    }

    private void checkContainId(final long id) {
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("삭제된 일기가 아닙니다");
        }
    }
}