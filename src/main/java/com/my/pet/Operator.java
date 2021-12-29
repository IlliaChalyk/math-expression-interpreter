package com.my.pet;

import static com.my.pet.Operator.Associativity.LEFT;
import static com.my.pet.Operator.Associativity.RIGHT;

enum Operator {
    ADD("+", 0, LEFT),
    SUBTRACT("-", 0, LEFT),
    MULTIPLY("*", 1, LEFT),
    DIVIDE("/", 1, LEFT),
    EXPONENT("^", 2, RIGHT),
    SQUARE_ROOT("sqrt", 2, LEFT),
    LEFT_PARENTHESIS("(", 3, LEFT),
    RIGHT_PARENTHESIS(")", 3, LEFT);

    public static Operator getByRepresentation(String representation) {
        for (Operator o : Operator.values()) {
            if (o.representation.equals(representation)) {
                return o;
            }
        }
        throw new IllegalArgumentException("There is no operator for representation: " + representation);
    }

    private final String representation;
    private final int precedence;
    private final Associativity associativity;

    Operator(String representation, int precedence, Associativity associativity) {
        this.precedence = precedence;
        this.representation = representation;
        this.associativity = associativity;
    }

    public int getPrecedence() {
        return precedence;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public enum Associativity {
        LEFT,
        RIGHT,
    }
}
