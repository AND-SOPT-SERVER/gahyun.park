package org.sopt.diary.error;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(final String errorMessages) {
        super(errorMessages);
    }

    public UnAuthorizedException() {
        super();
    }
}