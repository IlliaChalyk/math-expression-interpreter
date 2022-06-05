package com.my.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MathExpressionInterpreterTest {
    private static final MathContext MATH_CONTEXT = new MathContext(10, RoundingMode.DOWN);

    @Test
    public void shouldEvaluateAdditionWithOnes() {
        String expression = "2+2";
        BigDecimal expected = new BigDecimal(4, MATH_CONTEXT);
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateAdditionWithTens() {
        String expression = "22+22";
        BigDecimal expected = new BigDecimal(44, MATH_CONTEXT);
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateSubtraction() {
        String expression = "10-5";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal(5, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateMultiplication() {
        String expression = "20*2";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal(40, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateDivision() {
        String expression = "36/6";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal(6, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateExponent() {
        String expression = "5^2";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal(25, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateSquareRoot() {
        String expression = "sqrt(9)";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal(3, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateExpressionWithParenthesis() {
        String expression = "2*(5+5)";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal(20, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateExpressionWithSingleOperand() {
        String expression = "456";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal(456, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateComplexExpression() {
        String expression = "(11+3)/10+12^2*(sqrt(9)-1)+44+(-0.4)";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal(333, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(expected.setScale(1), actual.setScale(1));

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateExpressionWithSpacesBetweenOperatorsAndOperands() {
        String expression = "( 11   +  3) / 10+ 12^2  *(sqrt  ( 9 ) - 1)+ 44 +(-0.4)";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal(333, MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateExpressionWithHugeNumbers() {
        String expression = "(1000000000000000000000000+0.0000000000000000000001)";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal("1000000000000000000000000.0000000000000000000001", MATH_CONTEXT);
        System.out.println(expected);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldAddTwoDecimalsWithOmittedWholePart() {
        String expression = ".1+.1";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal("0.2", MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateExpressionInParenthesis() {
        String expression = "(12+12-4)";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal("20", MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateExpressionWithPositiveAndNegativeSignedOperands() {
        String expression = "(+12+12+(-4))";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal("20", MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldCountOmittedOperatorBetweenOperandAndLeftParenthesisAsMultiplication() {
        String expression = "4(2+2)";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal("16", MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldEvaluateExpressionWithFirstSignedOperandNotEnclosedInParenthesis() {
        String expression = "-1+2";
        MathExpressionInterpreter interpreter = MathExpressionInterpreter.create(expression);
        BigDecimal expected = new BigDecimal("1", MATH_CONTEXT);

        BigDecimal actual = interpreter.evaluate();

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    public void shouldThrowExceptionWithMalformedExpression() {
        String expression = "11+*5";
        assertThrows(MalformedExpressionException.class, () -> MathExpressionInterpreter.create(expression));
    }

    @Test
    public void shouldThrowExceptionIfNoOperands() {
        String expression = "*";
        assertThrows(MalformedExpressionException.class, () -> MathExpressionInterpreter.create(expression));
    }

    @Test
    public void shouldThrowAnExceptionIfExpressionIsEmpty() {
        String expression = "";
        assertThrows(MalformedExpressionException.class, () -> MathExpressionInterpreter.create(expression));
    }

    @Test
    public void shouldThrowAnExceptionIfExpressionContainsOnlySpaces() {
        String expression = " ";
        assertThrows(MalformedExpressionException.class, () -> MathExpressionInterpreter.create(expression));
    }

    @Test
    public void shouldThrowAnExceptionIfExpressionIsNull() {
        String expression = null;
        assertThrows(MalformedExpressionException.class, () -> MathExpressionInterpreter.create(expression));
    }

    @Test
    public void shouldThrowAnExceptionIfMultipleSignsInFrontOfOperand() {
        String expression = "(-+1)";
        assertThrows(MalformedExpressionException.class, () -> MathExpressionInterpreter.create(expression));
    }

    @Test
    public void shouldThrowExceptionIfIllegalCharactersPresent() {
        String expression = "hello10+1";
        assertThrows(MalformedExpressionException.class, () -> MathExpressionInterpreter.create(expression));
    }
}
