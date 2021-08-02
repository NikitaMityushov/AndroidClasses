package com.mityushov.calculator;

public abstract class AbstractCalculator<T> implements CalculatorAPI<T> {
    private T a;
    private T b;
    private Operations currentOperation = Operations.NONE;

    public T getA() {
        return a;
    }

    public void setA(T a) {
        this.a = a;
    }

    public T getB() {
        return b;
    }

    public void setB(T b) {
        this.b = b;
    }

    public Operations getCurrentOperation() {
        return currentOperation;
    }

    public void setCurrentOperation(Operations currentOperation) {
        this.currentOperation = currentOperation;
    }
}
