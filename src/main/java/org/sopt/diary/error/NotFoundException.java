package org.sopt.diary.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String errorMessages) {
        super(errorMessages);
    }
}

