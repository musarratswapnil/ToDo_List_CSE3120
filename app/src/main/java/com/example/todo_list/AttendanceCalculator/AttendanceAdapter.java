package com.example.todo_list.AttendanceCalculator;

public class AttendanceAdapter {
    private ExternalAttendanceLibrary externalLibrary;

    public AttendanceAdapter() {
        this.externalLibrary = new ExternalAttendanceLibrary();
    }

    public int calculateTotalClasses(int totalCredit, int totalWeeks) {
        return externalLibrary.computeTotalClasses(totalCredit, totalWeeks);
    }

    public double calculateAttendancePercentage(int classesAttended, int totalClasses) {
        return externalLibrary.computeAttendancePercentage(classesAttended, totalClasses);
    }

    public int calculateNeededClasses(int totalClasses, double desiredPercentage) {
        return externalLibrary.computeNeededClasses(totalClasses, desiredPercentage);
    }

    public int calculateRemainingClassesToAttend(int neededClasses, int classesAttended) {
        return externalLibrary.computeRemainingClassesToAttend(neededClasses, classesAttended);
    }
}

