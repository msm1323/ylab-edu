package ru.msm.edu.lesson_1;

import java.util.Scanner;

public class Stars {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        String c = scanner.next();
        scanner.close();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(c);
            }
            System.out.println();
        }

    }

}
