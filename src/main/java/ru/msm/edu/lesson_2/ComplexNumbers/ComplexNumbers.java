package ru.msm.edu.lesson_2.ComplexNumbers;

public class ComplexNumbers {

    private double real;
    private double imagine = 0;

    public double getReal() {
        return real;
    }

    public double getImagine() {
        return imagine;
    }

    public ComplexNumbers(double real) {
        this.real = real;
    }

    public ComplexNumbers(double real, double imagine) {
        this.real = real;
        this.imagine = imagine;
    }

    public ComplexNumbers add(ComplexNumbers other) {
        return new ComplexNumbers(this.real + other.getReal(), this.imagine + other.getImagine());
    }

    public ComplexNumbers subtract(ComplexNumbers other) {
        return new ComplexNumbers(this.real - other.getReal(), this.imagine - other.getImagine());
    }

    public ComplexNumbers multiply(ComplexNumbers other) {
        double newReal = this.real * other.getReal();
        if (this.imagine != 0 && other.getImagine() != 0) {
            newReal -= this.imagine * other.getImagine();
        }
        double newImagine = this.real * other.getImagine() + this.imagine * other.getReal();
        return new ComplexNumbers(newReal, newImagine);
    }

    public double abs() {
        return Math.sqrt(Math.pow(real, 2) + Math.pow(imagine, 2));
    }

    @Override
    public String toString() {
        return String.format("%f + %fi", this.real, this.imagine);
    }
}
