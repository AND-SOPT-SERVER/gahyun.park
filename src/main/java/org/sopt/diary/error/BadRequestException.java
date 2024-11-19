package org.sopt.diary.error;


public class BadRequestException extends RuntimeException {
    public BadRequestException(final String errorMessages) {
        super(errorMessages);
    }
}
