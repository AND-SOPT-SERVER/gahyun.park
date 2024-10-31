package org.sopt.diary;

import org.hibernate.NonUniqueResultException;
import org.sopt.diary.error.BadRequestException;
import org.sopt.diary.error.ForBiddenException;
import org.sopt.diary.error.NotFoundException;
import org.sopt.diary.error.UnAuthorizedException;
import org.sopt.diary.util.ErrorMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<String> handleBadRequestException(final Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<String> handleIllegalArgumentException(final Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    protected ResponseEntity<String> handleUnAuthorizedException(final UnAuthorizedException e) {
        return ResponseEntity.status(401).build();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    protected ResponseEntity<String> handleSQLIntegrityConstraintViolationException(final SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.status(400).body(ErrorMessages.INTEGRITY_CONSTRAINT);
    }
    // 광범위한 오류를 처리하는 거다 보니 데이터 무결성 위배라는 메시지를 전달함. 하지만 클라이언트에게 좀 더 명시적이게 중복된 제목이다, 중복된 ID다 라고 전달하고 싶은데 어떻게 하면 좋을지

    @ExceptionHandler(NonUniqueResultException.class)
    protected ResponseEntity<String> handleNonUniqueResultException(final NonUniqueResultException e) {
        return ResponseEntity.status(400).body(ErrorMessages.INTEGRITY_CONSTRAINT);
    }

    @ExceptionHandler(ForBiddenException.class)
    protected ResponseEntity<String> ForBiddenException(final ForBiddenException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return ResponseEntity.status(400).body(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<String> handleMethodArgumentNotValidException(final NotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}