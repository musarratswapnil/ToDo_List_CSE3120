/**
 * The AttendanceAdapter class provides methods to calculate attendance-related results and information.
 */
package com.example.todo_list.AttendanceCalculator;

public class AttendanceAdapter {

    /**
     * Calculates and returns the attendance result based on the provided parameters and logic.
     *
     * @param totalCredit        The total number of credit hours for the course.
     * @param totalWeeks         The total number of weeks in the course.
     * @param classesAttended    The number of classes attended so far.
     * @param desiredPercentage The desired attendance percentage.
     * @param remainingWeeks     The remaining weeks in the course.
     * @param calculatorLogic    The logic object implementing the attendance calculation logic.
     * @return A string containing the attendance result message.
     */
    public String getAttendanceResult(int totalCredit, int totalWeeks, int classesAttended, double desiredPercentage, int remainingWeeks, AttendanceCalculatorLogic calculatorLogic) {
        // Calculate total classes, total remaining classes, needed classes, and remaining classes to attend
        int totalClasses = calculatorLogic.calculateTotalClasses(totalCredit, totalWeeks);
        int totalRemainingClasses = calculatorLogic.calculateRemainingClasses(totalCredit, remainingWeeks);
        int neededClasses = calculatorLogic.calculateNeededClasses(totalCredit, totalWeeks, desiredPercentage);
        int remainingClassesToAttend = calculatorLogic.calculateRemainingClassesToAttend(totalCredit, totalWeeks, classesAttended, desiredPercentage);

        // Generate and return the appropriate result message
        if (!calculatorLogic.canAchieveDesiredPercentage(totalCredit, remainingWeeks, remainingClassesToAttend)) {
            return String.format("Sorry, you can't achieve %.2f%% attendance in %d weeks.", desiredPercentage, remainingWeeks);
        } else {
            if (remainingClassesToAttend > 0) {
                return String.format("You need to attend %d more classes to achieve %.2f%% attendance.", remainingClassesToAttend, desiredPercentage);
            } else {
                int extraClasses = classesAttended - neededClasses;
                return String.format("You have already achieved %.2f%% attendance and attended %d more classes than required.", desiredPercentage, extraClasses);
            }
        }
    }

    /**
     * Calculates and returns the total remaining classes based on the provided parameters and logic.
     *
     * @param totalCredit     The total number of credit hours for the course.
     * @param remainingWeeks  The remaining weeks in the course.
     * @param calculatorLogic The logic object implementing the attendance calculation logic.
     * @return The total number of remaining classes.
     */
    public int getTotalRemainingClasses(int totalCredit, int remainingWeeks, AttendanceCalculatorLogic calculatorLogic) {
        return calculatorLogic.calculateRemainingClasses(totalCredit, remainingWeeks);
    }

    /**
     * Calculates and returns the remaining classes to attend based on the provided parameters and logic.
     *
     * @param totalCredit        The total number of credit hours for the course.
     * @param totalWeeks         The total number of weeks in the course.
     * @param classesAttended    The number of classes attended so far.
     * @param desiredPercentage The desired attendance percentage.
     * @param calculatorLogic    The logic object implementing the attendance calculation logic.
     * @return The number of remaining classes to attend.
     */
    public int getRemainingClassesToAttend(int totalCredit, int totalWeeks, int classesAttended, double desiredPercentage, AttendanceCalculatorLogic calculatorLogic) {
        return calculatorLogic.calculateRemainingClassesToAttend(totalCredit, totalWeeks, classesAttended, desiredPercentage);
    }

    /**
     * Calculates and returns the attendance percentage based on the provided parameters and logic.
     *
     * @param totalCredit       The total number of credit hours for the course.
     * @param totalWeeks        The total number of weeks in the course.
     * @param classesAttended   The number of classes attended so far.
     * @param percentageLogic   The logic object implementing the attendance percentage calculation logic.
     * @return The calculated attendance percentage.
     */
    public double calculateAttendancePercentage(int totalCredit, int totalWeeks, int classesAttended, AttendancePercentageLogic percentageLogic) {
        return percentageLogic.calculatePercentage(totalCredit, totalWeeks, classesAttended);
    }
}
