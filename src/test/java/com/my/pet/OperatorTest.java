package com.my.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OperatorTest {
    @Test
    public void shouldReturnOperatorFromRepresentation() {
        Operator expected = Operator.ADD;

        Operator actual = Operator.getByRepresentation("+");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowExceptionIfNoOperatorForRepresentation() {
        String malformedRepresentation = "malformed";
        assertThrows(RuntimeException.class, () -> Operator.getByRepresentation(malformedRepresentation));
    }
}
