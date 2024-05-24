// AttendanceModel.java
package com.example.todo_list.AttendanceCalculator;

public class AttendanceModel {
    private int totalCredit;
    private int totalWeeks;
    private int classesAttended;
    private double desiredPercentage;
    private int remainingWeeks;

    public AttendanceModel(int totalCredit, int totalWeeks, int classesAttended, double desiredPercentage, int remainingWeeks) {
        this.totalCredit = totalCredit;
        this.totalWeeks = totalWeeks;
        this.classesAttended = classesAttended;
        this.desiredPercentage = desiredPercentage;
        this.remainingWeeks = remainingWeeks;
    }

    public int calculateTotalClasses() {
        return totalCredit * totalWeeks;
    }

    public int calculateRemainingClasses() {
        return totalCredit * remainingWeeks;
    }

    public int calculateNeededClasses() {
        return (int) Math.ceil((desiredPercentage / 100) * calculateTotalClasses());
    }

    public int calculateRemainingClassesToAttend() {
        return calculateNeededClasses() - classesAttended;
    }

    public boolean canAchieveDesiredPercentage() {
        return calculateRemainingClassesToAttend() <= calculateRemainingClasses();
    }
}
