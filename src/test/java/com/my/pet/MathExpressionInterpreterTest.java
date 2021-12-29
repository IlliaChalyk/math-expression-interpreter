package com.my.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MathExpressionInterpreterTest {
    private static final MathContext MATH_CONTEXT = new MathContext(10, RoundingMode.DOWN);

    /**
     * TODO add tests for:
     *  1) parenthesis around whole expression
     *  2) operand followed by "(" should count as multiplication
     *  3) operand inside parenthesis should ignore parenthesis
     *  4) various malformed expressions
     *  5) case with blank on null expressions
     */

    @Test
    public void shouldEvaluateAdditionWithOnes() {
        String expression = "2+2";
        BigDecimal expected = new BigDecimal(4, MATH_CONTEXT);
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateAdditionWithTens() {
        String expression = "22+22";
        BigDecimal expected = new BigDecimal(44, MATH_CONTEXT);
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateSubtraction() {
        String expression = "10-5";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal(5, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateMultiplication() {
        String expression = "20*2";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal(40, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateDivision() {
        String expression = "36/6";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal(6, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateExponent() {
        String expression = "5^2";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal(25, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateSquareRoot() {
        String expression = "sqrt(9)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal(3, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateExpressionWithParenthesis() {
        String expression = "2*(5+5)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal(20, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateExpressionWithSingleOperand() {
        String expression = "456";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal(456, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateComplexExpression() {
        String expression = "(11+3)/10+12^2*(sqrt(9)-1)+44+(-0.4)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal(333, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateExpressionWithSpacesBetweenOperatorsAndOperands() {
        String expression = "( 11   +  3) / 10+ 12^2  *(sqrt  ( 9 ) - 1)+ 44 +(-0.4)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal(333, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldEvaluateExpressionWithHugeNumbers() {
        String expression = "(1000000000000000000000000+0.0000000000000000000001)";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal("1000000000000000000000000.0000000000000000000001", MATH_CONTEXT);
        System.out.println(expected);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
    }

    @Test
    public void shouldAddTwoDecimalsWithOmittedWholePart() {
        String expression = ".1+.1";
        MathExpressionInterpreter interpreter = new MathExpressionInterpreter(expression);
        BigDecimal expected = new BigDecimal("0.2", MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.compareTo(actual), 0);
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
