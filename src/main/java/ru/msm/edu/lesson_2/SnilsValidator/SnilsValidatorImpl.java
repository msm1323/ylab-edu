package ru.msm.edu.lesson_2.SnilsValidator;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {
        if (snils.length() != 11 || !(Character.isDigit(snils.charAt(9)) && Character.isDigit(snils.charAt(10)))) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(snils.charAt(i))) {
                return false;
            }
            sum += Character.digit(snils.charAt(i), 10) * (9 - i);
        }
        int checkSum = Character.digit(snils.charAt(9), 10) * 10 + Character.digit(snils.charAt(10), 10);

        if (sum == 100) {
            sum = 0;
        } else if (sum > 100) {
            sum = sum % 101;
            if (sum == 100) {
                sum = 0;
            }
        }

        return sum == checkSum;
    }
}
