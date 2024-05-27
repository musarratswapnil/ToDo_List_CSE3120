package com.example.todo_list.CgpaCalculator;

/**
 * This class provides a factory method to create instances of GPA calculators.
 */
public class GpaCalculatorFactory {
    /**
     * Creates an instance of GpaCalculator based on the specified type.
     * @param type The type of GPA calculator to create. Valid values are "semester" and "cgpa".
     * @return A new GpaCalculator instance of the specified type.
     * @throws IllegalArgumentException if the specified type is unknown.
     */
    public static GpaCalculator createCalculator(String type) {
        switch (type) {
            case "semester":
                return new SemesterGpaCalculator();
            case "cgpa":
                return new CgpaCalculator();
            default:
                throw new IllegalArgumentException("Unknown calculator type");
        }
    }
}

