package com.example.todo_list.CgpaCalculator;

import java.util.ArrayList;

/**
 * This class implements the GpaCalculator interface to provide GPA calculation for a single semester.
 */
public class SemesterGpaCalculator implements GpaCalculator {

    /**
     * Calculates the GPA based on the provided grades and credits.
     * @param grades A list of grade values for each course.
     * @param credits A list of credit values for each course.
     * @return The calculated GPA. Returns 0.0 if total credits are zero to handle division by zero.
     */
    @Override
    public double calculate(ArrayList<Double> grades, ArrayList<Double> credits) {
        double totalGradePoints = 0;
        double totalCredits = 0;

        for (int i = 0; i < grades.size(); i++) {
            totalGradePoints += grades.get(i) * credits.get(i);
            totalCredits += credits.get(i);
        }
        if (totalCredits == 0) {
            return 0.0;  // Handle division by zero
        }

        return totalGradePoints / totalCredits;
    }
}
