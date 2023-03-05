package ru.msm.edu.lesson_1;

import java.util.Scanner;

public class Pell {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.close();
        System.out.println(searchPellNumber(n));

    }

    static public long searchPellNumber(int n) {
        long pellN = n;

        if (n > 2) {
            long pre_minus_1 = 1;
            long pre_minus_2 = 0;
            for (int i = 2; i <= n; i++) {
                pellN = pre_minus_1 * 2 + pre_minus_2;
                pre_minus_2 = pre_minus_1;
                pre_minus_1 = pellN;
            }
        }
        return pellN;
    }

}
