package ru.msm.edu.lesson_2.StatsAccumulator;

public class StatsAccumulatorTest {

    public static void main(String[] args) throws IllegalStateException {
        StatsAccumulator s = new StatsAccumulatorImpl();
        s.add(1);
        s.add(2);
        System.out.println(s.getAvg()); // 1.5 - среднее арифметическое элементов
        s.add(0);
        System.out.println(s.getMin()); // 0 - минимальное из переданных значений
        s.add(3);
        s.add(8);
        System.out.println(s.getMax()); // 8 - максимальный из переданных
        System.out.println(s.getCount()); // 5 - количество переданных элементов
    }

}
