package org.sopt.diary;

import org.sopt.diary.error.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<String> handleBadRequestException(final Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}