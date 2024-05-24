package com.example.todo_list.AttendanceCalculator;

public class ExternalAttendanceLibrary {
    public int computeTotalClasses(int credits, int weeks) {
        return credits * weeks;
    }

    public double computeAttendancePercentage(int attended, int total) {
        return (double) attended / total * 100;
    }

    public int computeNeededClasses(int totalClasses, double desiredPercentage) {
        return (int) Math.ceil((desiredPercentage / 100) * totalClasses);
    }

    public int computeRemainingClassesToAttend(int neededClasses, int classesAttended) {
        return neededClasses - classesAttended;
    }
}
