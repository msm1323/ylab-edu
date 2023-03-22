package ru.msm.edu.lesson_3.PasswordValidator;

public class WrongPasswordException extends Exception {

    public WrongPasswordException() {}

    public WrongPasswordException(String message) {
        super(message);
    }


}
