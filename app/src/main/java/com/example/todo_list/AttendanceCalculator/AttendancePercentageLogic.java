/**
 * The AttendancePercentageLogic class provides methods for calculating attendance percentage.
 */
package com.example.todo_list.AttendanceCalculator;

public class AttendancePercentageLogic {

    /**
     * Calculates the attendance percentage based on the total credit hours, total weeks, and classes attended.
     *
     * @param totalCredit    The total number of credit hours for the course.
     * @param totalWeeks     The total number of weeks in the course.
     * @param classesAttended The number of classes attended so far.
     * @return The calculated attendance percentage.
     */
    public double calculatePercentage(int totalCredit, int totalWeeks, int classesAttended) {
        int totalClasses = totalCredit * totalWeeks;
        return (double) classesAttended / totalClasses * 100;
    }
}
