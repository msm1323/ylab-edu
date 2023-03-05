package ru.msm.edu.lesson_1;

import java.util.Random;
import java.util.Scanner;

public class Guess {

    public static void main(String[] args) {
        int answer = new Random().nextInt(99) + 1;
        int attempts = 10;
        System.out.println("Я загадал число. У тебя " + attempts + " попыток угадать.");

        Scanner scanner = new Scanner(System.in);

        while (attempts > 0) {
            int guess = scanner.nextInt();
            attempts--;
            if (guess == answer) {
                System.out.printf("Ты угадал с %d попытки!\n", 10 - attempts);
                attempts = -1;
                scanner.close();
                break;
            } else if (guess < answer) {
                System.out.printf("Мое число больше! У тебя осталось %d попыток\n", attempts);
            } else {
                System.out.printf("Мое число меньше! У тебя осталось %d попыток\n", attempts);
            }
        }

        if (attempts != -1) {
            System.out.println("Ты не угадал.\n");
        }

    }

}
