package com.example.todo_list.CgpaCalculator;

import java.util.ArrayList;

public class SemesterGpaCalculator implements GpaCalculator {
    @Override
    public double calculate(ArrayList<Double> grades, ArrayList<Double> credits) {
        double totalGradePoints = 0;
        double totalCredits = 0;

        for (int i = 0; i < grades.size(); i++) {
            totalGradePoints += grades.get(i) * credits.get(i);
            totalCredits += credits.get(i);
        }

        return totalGradePoints / totalCredits;
    }
}
