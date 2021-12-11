package com.my.pet;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

public class MathExpressionInterpreter {
    private String expression;

    private Deque<BigDecimal> operands = new ArrayDeque<>();
    private Deque<Operator> operators = new ArrayDeque<>();


    public MathExpressionInterpreter(String expression) {
        this.expression = expression;
    }


    //TODO change return type to double or BigDecimal
    public int evaluate() {
        return Integer.MIN_VALUE;
    }
}
