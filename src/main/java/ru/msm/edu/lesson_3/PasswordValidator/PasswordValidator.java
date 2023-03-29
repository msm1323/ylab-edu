package ru.msm.edu.lesson_3.PasswordValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    static boolean validate(String login, String password, String confirmPassword) {
        try {
            Pattern p = Pattern.compile("\\w*");
            if (login.length() >= 20) {
                throw new WrongLoginException("Логин слишком длинный");
            }
            if (!p.matcher(login).matches()) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }

            if (password.length() >= 20) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }
            if (!p.matcher(password).matches()) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }
            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (WrongLoginException | WrongPasswordException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

}
