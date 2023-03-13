package ru.msm.edu.lesson_2.StatsAccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {

    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;
    private int count = 0;
    private double avg = 0;

    @Override
    public void add(int value) {
        count++;
        avg = (avg * (count - 1) + value) / count;
        min = Integer.min(min, value);
        max = Integer.max(max, value);
    }

    @Override
    public int getMin() {
        if (count == 0) {
            throw new IllegalStateException();
        }
        return min;
    }

    @Override
    public int getMax() {
        if (count == 0) {
            throw new IllegalStateException();
        }
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if (count == 0) {
            throw new IllegalStateException();
        }
        return avg;
    }
}
