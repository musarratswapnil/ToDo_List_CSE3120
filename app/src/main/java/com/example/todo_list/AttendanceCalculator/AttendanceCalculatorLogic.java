package com.example.todo_list.AttendanceCalculator;

public class AttendanceCalculatorLogic {

    public int calculateTotalClasses(int totalCredit, int totalWeeks) {
        return totalCredit * totalWeeks;
    }

    public int calculateRemainingClasses(int totalCredit, int remainingWeeks) {
        return totalCredit * remainingWeeks;
    }

    public int calculateNeededClasses(int totalCredit, int totalWeeks, double desiredPercentage) {
        int totalClasses = calculateTotalClasses(totalCredit, totalWeeks);
        return (int) Math.floor((desiredPercentage / 100) * totalClasses);
    }

    public int calculateRemainingClassesToAttend(int totalCredit, int totalWeeks, int classesAttended, double desiredPercentage) {
        int neededClasses = calculateNeededClasses(totalCredit, totalWeeks, desiredPercentage);
        return neededClasses - classesAttended;
    }

    public boolean canAchieveDesiredPercentage(int totalCredit, int remainingWeeks, int remainingClassesToAttend) {
        int totalRemainingClasses = calculateRemainingClasses(totalCredit, remainingWeeks);
        return remainingClassesToAttend <= totalRemainingClasses;
    }
}
