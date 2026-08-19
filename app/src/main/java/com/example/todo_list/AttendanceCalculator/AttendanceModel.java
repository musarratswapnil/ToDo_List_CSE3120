/**
 * The AttendanceModel class represents a model for attendance-related data.
 */
package com.example.todo_list.AttendanceCalculator;

public class AttendanceModel {
    private int totalCredit;
    private int totalWeeks;
    private int classesAttended;
    private double desiredPercentage;
    private int remainingWeeks;

    /**
     * Constructs an instance of AttendanceModel with the specified parameters.
     *
     * @param totalCredit       The total number of credit hours for the course.
     * @param totalWeeks        The total number of weeks in the course.
     * @param classesAttended   The number of classes attended so far.
     * @param desiredPercentage The desired attendance percentage.
     * @param remainingWeeks    The remaining weeks in the course.
     */
    public AttendanceModel(int totalCredit, int totalWeeks, int classesAttended, double desiredPercentage, int remainingWeeks) {
        this.totalCredit = totalCredit;
        this.totalWeeks = totalWeeks;
        this.classesAttended = classesAttended;
        this.desiredPercentage = desiredPercentage;
        this.remainingWeeks = remainingWeeks;
    }

    /**
     * Calculates the total number of classes based on total credit hours and total weeks.
     *
     * @return The total number of classes.
     */
    public int calculateTotalClasses() {
        return totalCredit * totalWeeks;
    }

    /**
     * Calculates the remaining classes based on total credit hours and remaining weeks.
     *
     * @return The number of remaining classes.
     */
    public int calculateRemainingClasses() {
        return totalCredit * remainingWeeks;
    }

    /**
     * Calculates the needed classes to achieve a desired attendance percentage.
     *
     * @return The number of needed classes.
     */
    public int calculateNeededClasses() {
        return (int) Math.ceil((desiredPercentage / 100) * calculateTotalClasses());
    }

    /**
     * Calculates the remaining classes to attend to achieve a desired attendance percentage.
     *
     * @return The number of remaining classes to attend.
     */
    public int calculateRemainingClassesToAttend() {
        return calculateNeededClasses() - classesAttended;
    }

    /**
     * Checks if it's feasible to achieve the desired attendance percentage.
     *
     * @return True if it's feasible, false otherwise.
     */
    public boolean canAchieveDesiredPercentage() {
        return calculateRemainingClassesToAttend() <= calculateRemainingClasses();
    }
}
