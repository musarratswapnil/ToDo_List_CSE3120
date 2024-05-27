package com.example.todo_list.AttendanceCalculator;

public class AttendanceAdapter {

    public String getAttendanceResult(int totalCredit, int totalWeeks, int classesAttended, double desiredPercentage, int remainingWeeks, AttendanceCalculatorLogic calculatorLogic) {
        int totalClasses = calculatorLogic.calculateTotalClasses(totalCredit, totalWeeks);
        int totalRemainingClasses = calculatorLogic.calculateRemainingClasses(totalCredit, remainingWeeks);
        int neededClasses = calculatorLogic.calculateNeededClasses(totalCredit, totalWeeks, desiredPercentage);
        int remainingClassesToAttend = calculatorLogic.calculateRemainingClassesToAttend(totalCredit, totalWeeks, classesAttended, desiredPercentage);

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

    public int getTotalRemainingClasses(int totalCredit, int remainingWeeks, AttendanceCalculatorLogic calculatorLogic) {
        return calculatorLogic.calculateRemainingClasses(totalCredit, remainingWeeks);
    }

    public int getRemainingClassesToAttend(int totalCredit, int totalWeeks, int classesAttended, double desiredPercentage, AttendanceCalculatorLogic calculatorLogic) {
        return calculatorLogic.calculateRemainingClassesToAttend(totalCredit, totalWeeks, classesAttended, desiredPercentage);
    }

    public double calculateAttendancePercentage(int totalCredit, int totalWeeks, int classesAttended, AttendancePercentageLogic percentageLogic) {
        return percentageLogic.calculatePercentage(totalCredit, totalWeeks, classesAttended);
    }
}
