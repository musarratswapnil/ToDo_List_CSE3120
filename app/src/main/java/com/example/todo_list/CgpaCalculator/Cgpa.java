package com.example.todo_list.CgpaCalculator;

public class Cgpa {
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
