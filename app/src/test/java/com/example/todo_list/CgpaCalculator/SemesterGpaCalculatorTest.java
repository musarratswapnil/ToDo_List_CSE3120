package com.example.todo_list.CgpaCalculator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SemesterGpaCalculatorTest {

    private SemesterGpaCalculator calculator;

    @Before
    public void setUp() {
        calculator = new SemesterGpaCalculator();
    }

    @Test
    public void testCalculateWithValidData() {
        ArrayList<Double> grades = new ArrayList<>();
        grades.add(4.0);
        grades.add(3.75);
        grades.add(3.5);

        ArrayList<Double> credits = new ArrayList<>();
        credits.add(3.0);
        credits.add(3.0);
        credits.add(4.0);

        double result = calculator.calculate(grades, credits);
        assertEquals(3.725, result, 0.01);
    }

    @Test
    public void testCalculateWithZeroCredits() {
        ArrayList<Double> grades = new ArrayList<>();
        grades.add(4.0);
        grades.add(3.75);

        ArrayList<Double> credits = new ArrayList<>();
        credits.add(0.0);
        credits.add(0.0);

        double result = calculator.calculate(grades, credits);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateWithEmptyLists() {
        ArrayList<Double> grades = new ArrayList<>();
        ArrayList<Double> credits = new ArrayList<>();

        double result = calculator.calculate(grades, credits);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateWithMismatchedLists() {
        ArrayList<Double> grades = new ArrayList<>();
        grades.add(4.0);
        grades.add(3.75);

        ArrayList<Double> credits = new ArrayList<>();
        credits.add(3.0);

        try {
            calculator.calculate(grades, credits);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index 1 out of bounds for length 1", e.getMessage());
        }
    }
}
