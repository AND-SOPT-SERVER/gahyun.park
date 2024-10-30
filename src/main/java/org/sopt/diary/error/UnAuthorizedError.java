package org.sopt.diary.error;

public class UnAuthorizedError extends RuntimeException {
    public UnAuthorizedError(final String errorMessages) {
        super(errorMessages);
    }

    public UnAuthorizedError() {
        super();
    }
}