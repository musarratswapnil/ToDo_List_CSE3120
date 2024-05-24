package com.example.todo_list.CgpaCalculator;

public class GpaCalculatorFactory {
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

