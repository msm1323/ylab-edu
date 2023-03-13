package ru.msm.edu.lesson_2.Sequences;

public class SequenceGeneratorTest {

    public static void main(String[] args) throws IllegalArgumentException {
        SequenceGeneratorImpl sequenceGenerator = new SequenceGeneratorImpl();

        System.out.println("A. 2, 4, 6, 8, 10...");
        sequenceGenerator.a(5);
        System.out.println();
        sequenceGenerator.a(1);
        System.out.println();

        System.out.println("B. 1, 3, 5, 7, 9...");
        sequenceGenerator.b(5);
        System.out.println();
        sequenceGenerator.b(1);
        System.out.println();

        System.out.println("C. 1, 4, 9, 16, 25...");
        sequenceGenerator.c(5);
        System.out.println();
        sequenceGenerator.c(1);
        System.out.println();

        System.out.println("D. 1, 8, 27, 64, 125...");
        sequenceGenerator.d(5);
        System.out.println();
        sequenceGenerator.d(1);
        System.out.println();

        System.out.println("E. 1, -1, 1, -1, 1, -1...");
        sequenceGenerator.e(6);
        System.out.println();
        sequenceGenerator.e(1);
        System.out.println();

        System.out.println("F. 1, -2, 3, -4, 5, -6...");
        sequenceGenerator.f(6);
        System.out.println();
        sequenceGenerator.f(1);
        System.out.println();

        System.out.println("G. 1, -4, 9, -16, 25...");
        sequenceGenerator.g(5);
        System.out.println();
        sequenceGenerator.g(1);
        System.out.println();

        System.out.println("H. 1, 0, 2, 0, 3, 0, 4...");
        sequenceGenerator.h(7);
        System.out.println();
        sequenceGenerator.h(1);
        System.out.println();

        System.out.println("I. 1, 2, 6, 24, 120, 720...");
        sequenceGenerator.i(6);
        System.out.println();
        sequenceGenerator.i(1);
        System.out.println();

        System.out.println("J. 1, 1, 2, 3, 5, 8, 13, 21...");
        sequenceGenerator.j(8);
        System.out.println();
        sequenceGenerator.j(1);
        System.out.println();

    }

}
