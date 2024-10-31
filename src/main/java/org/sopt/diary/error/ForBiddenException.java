package org.sopt.diary.error;

public class ForBiddenException extends RuntimeException {
    public ForBiddenException(final String errorMessages) {
        super(errorMessages);
    }
}