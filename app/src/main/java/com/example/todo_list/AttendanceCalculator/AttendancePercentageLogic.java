package com.example.todo_list.AttendanceCalculator;

public class AttendancePercentageLogic {

    public double calculatePercentage(int totalCredit, int totalWeeks, int classesAttended) {
        int totalClasses = totalCredit * totalWeeks;
        return (double) classesAttended / totalClasses * 100;
    }
}
