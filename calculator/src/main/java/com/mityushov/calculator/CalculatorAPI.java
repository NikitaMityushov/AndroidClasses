package com.mityushov.calculator;

public interface CalculatorAPI<T> {
    T calculate();

    enum Operations {
        ADD,
        SUB,
        MULT,
        DIV,
        NONE
    }
}
