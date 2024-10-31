package org.sopt.diary.repository;

public enum Category {
    MOVIE,
    EXERCISE,
    FOOD,
    SCHOOL;

    public static boolean isPresent(String category) {
        try {
            Category.valueOf(category.toUpperCase()); // 문자열을 Enum으로 변환 시도
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
}