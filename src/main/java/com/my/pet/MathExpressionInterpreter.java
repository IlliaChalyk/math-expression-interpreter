package com.my.pet;

import static com.my.pet.Operator.Associativity.LEFT;
import static com.my.pet.Operator.LEFT_PARENTHESIS;
import static com.my.pet.Operator.RIGHT_PARENTHESIS;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: refactoring
public class MathExpressionInterpreter {
    private static final String ARGUMENTS_REGEX =
            "(?<functionName>sqrt)|(?<functionBody>(?<=sqrt)\\(.*?\\))|(?<number>((?<=\\()[-+])?\\d*\\.?\\d+)|(?<operator>[+\\-*/()^])";
    private static final int DEFAULT_PRECISION = 10;

    private final MathContext mathContext;
    private final String expression;

    private Queue<Object> reversePolishNotation = new ArrayDeque<>();
    private Deque<Operator> operators = new ArrayDeque<>();

    public MathExpressionInterpreter(String expression) {
        this.mathContext = new MathContext(DEFAULT_PRECISION, RoundingMode.DOWN);
        this.expression = expression;
    }

    public MathExpressionInterpreter(String expression, MathContext mathContext) {
        this.mathContext = mathContext;
        this.expression = expression;
    }

    public BigDecimal evaluate() {
        try {
            parseExpressionToReversePolishNotation();
            return compute();
        } catch (Exception ex) {
            throw new MalformedExpressionException("Malformed expression: " + expression, ex);
        }
    }

    private void parseExpressionToReversePolishNotation() {
        Pattern pattern = Pattern.compile(ARGUMENTS_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expression);

        matcher.results()
                .map(MatchResult::group)
                .forEach(arg -> {
                    //TODO: extract to separate method
                    if (isNumerical(arg)) {
                        reversePolishNotation.add(new BigDecimal(arg));
                    } else if (isFunctionSubExpression(arg)) {
                        BigDecimal res = new MathExpressionInterpreter(arg).evaluate();
                        reversePolishNotation.add(res);
                    } else {
                        processOperator(arg);
                    }
                });
        //TODO: rename it to something like #addToRPN
        addRestOperators();
    }

    private boolean isFunctionSubExpression(String op) {
        return op.matches("\\(.*\\)");
    }

    private void processOperator(String op) {
        Operator operator = Operator.getByRepresentation(op);
        Operator stackedOperator = operators.peek();

        if (operator == RIGHT_PARENTHESIS) {
            processSubExpression(stackedOperator);
            return;
        }

        while (!operators.isEmpty()) {
            if (stackedOperator != LEFT_PARENTHESIS
                    && operator.getPrecedence() <= stackedOperator.getPrecedence()
                    && operator.getAssociativity() == LEFT) {
                reversePolishNotation.add(operators.pop());
                stackedOperator = operators.peek();
            } else {
                break;
            }
        }
        operators.push(operator);
    }

    private void processSubExpression(Operator stackedOperator) {
        while (stackedOperator != LEFT_PARENTHESIS) {
            reversePolishNotation.add(operators.pop());
            stackedOperator = operators.peek();
        }
        if (stackedOperator == LEFT_PARENTHESIS) {
            operators.pop();
        }
    }

    private BigDecimal compute() {
        Deque<BigDecimal> stack = new ArrayDeque<>();
        for (Object op : reversePolishNotation) {
            if (op instanceof Operator) {
                BigDecimal secondOperand = stack.pop();
                switch ((Operator) op) {
                    case ADD:
                        stack.push(stack.pop().add(secondOperand, mathContext));
                        break;
                    case SUBTRACT:
                        stack.push(stack.pop().subtract(secondOperand, mathContext));
                        break;
                    case MULTIPLY:
                        stack.push(stack.pop().multiply(secondOperand, mathContext));
                        break;
                    case DIVIDE:
                        stack.push(stack.pop().divide(secondOperand, mathContext));
                        break;
                    case EXPONENT:
                        stack.push(stack.pop().pow(secondOperand.intValue(), mathContext));
                        break;
                    case SQUARE_ROOT:
                        stack.push(secondOperand.sqrt(mathContext));
                        break;
                }
            } else {
                stack.push((BigDecimal) op);
            }
        }
        return stack.pop();
    }

    private void addRestOperators() {
        for (Operator op : operators) {
            reversePolishNotation.add(op);
        }
    }

    private boolean isNumerical(String op) {
        return op.matches("[-+]?\\d*\\.?\\d+");
    }
}
