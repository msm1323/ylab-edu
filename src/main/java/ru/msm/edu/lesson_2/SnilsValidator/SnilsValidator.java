package ru.msm.edu.lesson_2.SnilsValidator;

public interface SnilsValidator {

    /**
     * Проверяет, что в строке содержится валидный номер СНИЛС
     * @param snils снилс
     * @return результат проверки
     */
    boolean validate(String snils);

}
