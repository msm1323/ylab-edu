package ru.msm.edu.lesson_2.SnilsValidator;

public class SnilsValidatorTest {

    public static void main(String[] args) {
        System.out.println(new SnilsValidatorImpl().validate("01468870570")); //false
        System.out.println(new SnilsValidatorImpl().validate("90114404441")); //true
        System.out.println();

        System.out.println(new SnilsValidatorImpl().validate("901141")); //false
        System.out.println(new SnilsValidatorImpl().validate("901141901141901141")); //false
        System.out.println(new SnilsValidatorImpl().validate("fghj9-011-41")); //false
        System.out.println(new SnilsValidatorImpl().validate("Dlkj;lfjg ;lfjs;")); //false
        System.out.println();

        // from https://ortex.github.io/snils-generator/
        System.out.println(new SnilsValidatorImpl().validate("62075521872")); //true
        System.out.println(new SnilsValidatorImpl().validate("74276596644")); //true
        System.out.println(new SnilsValidatorImpl().validate("02161981429")); //true
        System.out.println(new SnilsValidatorImpl().validate("87565700454")); //true
    }

}
