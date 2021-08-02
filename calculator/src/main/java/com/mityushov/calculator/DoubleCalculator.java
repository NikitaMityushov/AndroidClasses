package com.mityushov.calculator;

public class DoubleCalculator extends AbstractCalculator<Double> {

    public DoubleCalculator() {
        super.setA(0.0d);
        super.setB(0.0d);
    }

    private Double add() {
        return getA() + getB();
    }

    private Double sub() {
        return getA()  - getB();
    }

    private Double multiply() {
        return getA() * getB();
    }

    private Double div() {
        return getA() / getB();
    }

    @Override
    public Double calculate() {
        switch (getCurrentOperation()) {
            case ADD:
                 return add();
            case SUB:
                return sub();
            case MULT:
                return multiply();
            case DIV:
                return div();
            default:
                return getA();
        }
    }
}
