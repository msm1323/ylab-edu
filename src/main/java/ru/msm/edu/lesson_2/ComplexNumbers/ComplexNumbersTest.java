package ru.msm.edu.lesson_2.ComplexNumbers;

public class ComplexNumbersTest {

    public static void main(String[] args) {
        System.out.println("1.Создание нового числа по действительной части (конструктор с 1 параметром)");
        ComplexNumbers complexNumbers_1 = new ComplexNumbers(25.8);
        System.out.println(complexNumbers_1);
        System.out.println();

        System.out.println("2. Создание нового числа по действительной и мнимой части (конструктор с 2 параметрами)");
        ComplexNumbers complexNumbers_2 = new ComplexNumbers(226.821, 5);
        System.out.println(complexNumbers_2);
        System.out.println();

        System.out.println("3. Сложение");
        System.out.println(complexNumbers_1.add(complexNumbers_2));
        System.out.println();

        System.out.println("4. Вычитание");
        System.out.println(complexNumbers_2.subtract(complexNumbers_1));
        System.out.println();

        System.out.println("5. Умножение");
        System.out.println((new ComplexNumbers(4, 6).multiply(new ComplexNumbers(3))));
        System.out.println((new ComplexNumbers(4, 6).multiply(new ComplexNumbers(2, 6))));
        System.out.println();

        System.out.println("6. Операция получения модуля");
        System.out.println((new ComplexNumbers(4, 3).abs()));

    }

}
