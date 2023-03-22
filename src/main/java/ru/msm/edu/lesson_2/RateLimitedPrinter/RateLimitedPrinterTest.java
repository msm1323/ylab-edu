package ru.msm.edu.lesson_2.RateLimitedPrinter;

public class RateLimitedPrinterTest {

    public static void main(String[] args) {
        RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinter(2000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter.print(String.valueOf(i));
        }
    }

}
