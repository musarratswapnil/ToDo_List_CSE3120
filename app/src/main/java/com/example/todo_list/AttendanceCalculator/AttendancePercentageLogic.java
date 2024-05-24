package com.example.todo_list.AttendanceCalculator;

public class AttendancePercentageLogic {

    private AttendanceAdapter attendanceAdapter;

    public AttendancePercentageLogic(AttendanceAdapter attendanceAdapter) {
        this.attendanceAdapter = attendanceAdapter;
    }

    public double calculatePercentage(int totalCredit, int totalWeeks, int classesAttended) {
        int totalClasses = attendanceAdapter.calculateTotalClasses(totalCredit, totalWeeks);
        return attendanceAdapter.calculateAttendancePercentage(classesAttended, totalClasses);
    }
}
