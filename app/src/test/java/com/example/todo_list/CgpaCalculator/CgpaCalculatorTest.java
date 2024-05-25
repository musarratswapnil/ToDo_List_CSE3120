package com.example.todo_list.CgpaCalculator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

    public class CgpaCalculatorTest {

        private CgpaCalculator calculator;

        @Before
        public void setUp() {
            calculator = new CgpaCalculator();
        }

        @Test
        public void testCalculateWithValidData() {
            ArrayList<Double> grades = new ArrayList<>();
            grades.add(3.75);
            grades.add(3.50);
            grades.add(3.25);

            ArrayList<Double> credits = new ArrayList<>();
            credits.add(15.0);
            credits.add(18.0);
            credits.add(20.0);

            double result = calculator.calculate(grades, credits);
            assertEquals(3.476, result, 0.01); // Expected CGPA: (3.75*15 + 3.50*18 + 3.25*20) / (15 + 18 + 20)
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
            credits.add(15.0);

            try {
                calculator.calculate(grades, credits);
            } catch (IndexOutOfBoundsException e) {
                assertEquals("Index 1 out of bounds for length 1", e.getMessage());
            }
        }
    }


