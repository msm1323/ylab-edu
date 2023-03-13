package ru.msm.edu.lesson_2.RateLimitedPrinter;

import java.util.Date;

public class RateLimitedPrinter {

    private int interval;
    private long lastPrintTime = -1;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
    }

    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastPrintTime) > interval || lastPrintTime == -1) {
            lastPrintTime = currentTime;
//            System.out.println(message);
            System.out.println(message + " " + new Date());
        }
    }

}
