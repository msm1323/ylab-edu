package ru.msm.edu.lesson_3.PasswordValidator;

public class PasswordValidatorTest {

    public static void main(String[] args) {
        //true
        System.out.println(PasswordValidator.validate("Dududu","89hhuuk","89hhuuk"));
        System.out.println(PasswordValidator.validate("","", ""));
        System.out.println(PasswordValidator.validate("s","j","j"));
        System.out.println(PasswordValidator.validate("s_","8","8"));
        System.out.println(PasswordValidator.validate("s35","___","___"));
        System.out.println(PasswordValidator.validate("_s_9","_s_9","_s_9"));
        System.out.println(PasswordValidator.validate("asdfghjkl1234567890",
                "ASDFGHJKL1234567890","ASDFGHJKL1234567890"));
        System.out.println();

        //false
//        Логин содержит недопустимые символы
        System.out.println(PasswordValidator.validate("dududuЩ","89hhuuk","89hhuuk"));
        System.out.println(PasswordValidator.validate("s*_","8","8"));

//        Пароль содержит недопустимые символы
        System.out.println(PasswordValidator.validate("s","j_съезд","j_съезд"));
        System.out.println(PasswordValidator.validate("s35","!!___","!!___"));
        System.out.println(PasswordValidator.validate("_s_9","пробка_s_9","_"));

//        Логин слишком длинный
        System.out.println(PasswordValidator.validate("Qasdfghjkl1234567890",
                "ASDFGHJKL1234567890","ASDFGHJKL1234567890"));

//        Пароль слишком длинный
        System.out.println(PasswordValidator.validate("asdfghjkl1234567890",
                "ASDFGHJKL1234567890Q","ASDFGHJKL1234567890Q"));

//        !password.equals(confirmPassword)
        System.out.println(PasswordValidator.validate("asdfghjkl1234567890",
                "ASDFGHJKL1234567890","ASDFGHJKL123456789_"));
    }

}
