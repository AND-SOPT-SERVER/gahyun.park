package org.sopt.diary.util;

import org.sopt.diary.error.BadRequestException;

public class Validator {
    private static final int TITLE_MAX = 10;

    private static final int CONTENT_MAX = 30;

    public static void validTitleLength(final String text) {
        if (text.isEmpty() || text.length() > TITLE_MAX) {
            throw new BadRequestException(ErrorMessages.INVALID_TITLE_LENGTH);
        }
    }

    public static void validContentLength(final String text) {
        if (text.isEmpty() || text.length() > CONTENT_MAX) {
            throw new BadRequestException(ErrorMessages.INVALID_CONTENT_LENGTH);
        }
    }
}
