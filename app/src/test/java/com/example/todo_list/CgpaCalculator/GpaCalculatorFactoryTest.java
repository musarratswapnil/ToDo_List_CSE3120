package com.example.todo_list.CgpaCalculator;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

public class GpaCalculatorFactoryTest {

    @Test
    public void testCreateSemesterCalculator() {
        GpaCalculator calculator = GpaCalculatorFactory.createCalculator("semester");
        assertTrue(calculator instanceof SemesterGpaCalculator);
    }

    @Test
    public void testCreateCgpaCalculator() {
        GpaCalculator calculator = GpaCalculatorFactory.createCalculator("cgpa");
        assertTrue(calculator instanceof CgpaCalculator);
    }

    @Test
    public void testCreateUnknownCalculator() {
        assertThrows(IllegalArgumentException.class, () -> {
            GpaCalculatorFactory.createCalculator("unknown");
        });
    }
}
