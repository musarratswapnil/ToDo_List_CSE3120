package com.example.todo_list.CgpaCalculator;
/**
 * This class provides a method to convert letter grades to grade point values.
 */
public class Cgpa {
    /**
     * Converts a letter grade to its corresponding grade point value.
     * @param grade The letter grade (e.g., "A+", "A", "A-", etc.).
     * @return The grade point value corresponding to the letter grade.
     *         Returns 0.0 if the grade is not recognized.
     */
    protected double getGradeValue(String grade) {
        switch (grade) {
            case "A+":
                return 4.0;
            case "A":
                return 3.75;
            case "A-":
                return 3.5;
            case "B+":
                return 3.25;
            case "B":
                return 3.0;
            case "B-":
                return 2.75;
            case "C":
                return 2.5;
            case "D":
                return 2.25;
            default:
                return 0.0;
        }
    }

}
