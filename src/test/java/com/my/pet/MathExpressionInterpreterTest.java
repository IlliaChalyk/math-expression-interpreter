package com.my.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class MathExpressionInterpreterTest {

    /**
     * TODO add tests for:
     *  1) parenthesis around whole expression
     *  2) operand followed by "(" should count as multiplication
     *  3) operand inside parenthesis should ignore parenthesis
     *  4) various malformed expressions
     */

    @Test
    public void shouldEvaluateAddition() {
        String expression = "2+2";
        int expected = 4;
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateSubtraction() {
        String expression = "10-5";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        int expected = 5;

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateMultiplication() {
        String expression = "20*2";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        int expected = 40;

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateDivision() {
        String expression = "36/6";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        int expected = 6;

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateExponent() {
        String expression = "5^2";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        int expected = 25;

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateSquareRoot() {
        String expression = "sqrt(9)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        int expected = 3;

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateExpressionWithParenthesis() {
        String expression = "2*(5+5)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        int expected = 20;

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateExpressionWithSingleOperand() {
        String expression = "456";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        int expected = 456;

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateComplexExpression() {
        String expression = "(11+3)/10+12^2*(sqrt(9)-1)+44+(-0.4)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        int expected = 333;

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateExpressionWithSpacesBetweenOperatorsAndOperands() {
        String expression = "(  11+3)/10 + 12 ^2 (sqrt ( 9)-1)+   44+( -0.4)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        int expected = 333;

        int actual = interpreter.evaluate();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldEvaluateExpressionWithHugeNumbers() {
        //TODO
        String expression = "(1165694732256345764347567+344444444)/10+12^2(sqrt(743)-92822)+44+(-0.4040340257345)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal("116569470000000000000000");

        BigDecimal actual = new BigDecimal(interpreter.evaluate());

        assertTrue(expected.equals(actual));
    }

    @Test
    public void shouldThrowExceptionWithMalformedExpression() {
        String expression = "11+*5";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);

        assertThrows(MalformedExpressionException.class, interpreter::evaluate);
    }

    @Test
    public void shouldThrowExceptionIfNoOperands() {
        String expression = "*";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);

        assertThrows(MalformedExpressionException.class, interpreter::evaluate);
    }
}
