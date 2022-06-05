package com.my.pet;

public class MalformedExpressionException extends RuntimeException {
    public MalformedExpressionException(String message) {
        super(message);
    }

    public MalformedExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
