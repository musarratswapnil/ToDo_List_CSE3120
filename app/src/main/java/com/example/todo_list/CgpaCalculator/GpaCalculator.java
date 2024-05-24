package com.example.todo_list.CgpaCalculator;

import java.util.ArrayList;

public interface GpaCalculator {
    double calculate(ArrayList<Double> grades, ArrayList<Double> credits);
}
/*package com.example.todo_list.CgpaCalculator;

import java.util.ArrayList;

public class HighSchoolGpaCalculator implements GpaCalculator {
    @Override
    public double calculate(ArrayList<Double> grades, ArrayList<Double> credits) {
        double totalGradePoints = 0;
        double totalCredits = 0;

        for (int i = 0; i < grades.size(); i++) {
            double weight = 1.0;
            if (isHonorsOrAPClass(i)) {
                weight = 1.5; // Apply weight for honors or AP classes
            }
            totalGradePoints += grades.get(i) * credits.get(i) * weight;
            totalCredits += credits.get(i);
        }

        if (totalCredits == 0) {
            return 0.0;  // Handle division by zero
        }

        return totalGradePoints / totalCredits;
    }

    private boolean isHonorsOrAPClass(int index) {
        // Custom logic to determine if a course is an honors or AP class
        // This could be based on course name or another indicator
        // For simplicity, let's assume every third course is an honors/AP class
        return index % 3 == 0;
    }
}
*/
/*package com.example.todo_list.CgpaCalculator;

import java.util.ArrayList;

public class CollegeGpaCalculator implements GpaCalculator {
    @Override
    public double calculate(ArrayList<Double> grades, ArrayList<Double> credits) {
        double totalGradePoints = 0;
        double totalCredits = 0;

        for (int i = 0; i < grades.size(); i++) {
            double gradeValue = getCollegeGradeValue(grades.get(i));
            totalGradePoints += gradeValue * credits.get(i);
            totalCredits += credits.get(i);
        }

        if (totalCredits == 0) {
            return 0.0;  // Handle division by zero
        }

        return totalGradePoints / totalCredits;
    }

    private double getCollegeGradeValue(double grade) {
        // Custom logic for college grade values
        if (grade >= 90) {
            return 4.0;
        } else if (grade >= 85) {
            return 3.75;
        } else if (grade >= 80) {
            return 3.5;
        } else if (grade >= 75) {
            return 3.0;
        } else if (grade >= 70) {
            return 2.5;
        } else if (grade >= 65) {
            return 2.0;
        } else {
            return 0.0;
        }
    }
}
*/