package ru.msm.edu.lesson_2.ComplexNumbers;

public class ComplexNumbers {

    private double a;
    private double b = 0;

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public ComplexNumbers(double a) {
        this.a = a;
    }

    public ComplexNumbers(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public ComplexNumbers add(ComplexNumbers complexNumbers) {
        return new ComplexNumbers(this.a + complexNumbers.getA(), this.b + complexNumbers.getB());
    }

    public ComplexNumbers subtract(ComplexNumbers complexNumbers) {
        return new ComplexNumbers(this.a - complexNumbers.getA(), this.b - complexNumbers.getB());
    }

    public ComplexNumbers multiply(ComplexNumbers cNum) {
        double a_ = this.a * cNum.getA();
        if (this.b != 0 && cNum.getB() != 0) {
            a_ -= this.b * cNum.getB();
        }
        double b_ = this.a * cNum.getB() + this.b * cNum.getA();
        return new ComplexNumbers(a_, b_);
    }

    public double abs() {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    @Override
    public String toString() {
        return  String.format("%f + %fi", this.a, this.b);
    }
}
