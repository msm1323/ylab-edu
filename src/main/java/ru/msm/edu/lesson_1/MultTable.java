package ru.msm.edu.lesson_1;

public class MultTable {

    public static void main(String[] args) {

        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                System.out.printf("%d x %d = %d", i, j, i * j);
                System.out.println();
            }
            System.out.println();
        }

    }

}
