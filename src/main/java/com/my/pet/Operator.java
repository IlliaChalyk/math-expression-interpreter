package com.my.pet;

public enum Operator {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    EXPONENT("^"),
    SQUARE_ROOT("sqrt()"),
    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")");

    public static Operator getByRepresentation(String representation) {
        for(Operator o : Operator.values()) {
            if (o.representation.equals(representation)) {
                return o;
            }
        }
        throw new IllegalArgumentException("There is no operator for representation: " + representation);
    }

    private String representation;

    Operator(String representation) {
        this.representation = representation;
    }

}
