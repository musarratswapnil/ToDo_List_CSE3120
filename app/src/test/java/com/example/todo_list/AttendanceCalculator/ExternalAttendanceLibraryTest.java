package com.example.todo_list.AttendanceCalculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ExternalAttendanceLibraryTest {

    private final ExternalAttendanceLibrary attendanceLibrary = new ExternalAttendanceLibrary();

    @Test
    public void testComputeTotalClasses() {
        assertEquals(45, attendanceLibrary.computeTotalClasses(3, 15));
        assertEquals(30, attendanceLibrary.computeTotalClasses(2, 15));
        assertEquals(60, attendanceLibrary.computeTotalClasses(4, 15));
    }

    @Test
    public void testComputeAttendancePercentage() {
        assertEquals(50.0, attendanceLibrary.computeAttendancePercentage(10, 20), 0.01);
        assertEquals(75.0, attendanceLibrary.computeAttendancePercentage(15, 20), 0.01);
        assertEquals(100.0, attendanceLibrary.computeAttendancePercentage(20, 20), 0.01);
    }

    @Test
    public void testComputeNeededClasses() {
        assertEquals(34, attendanceLibrary.computeNeededClasses(45, 75.0));
        assertEquals(15, attendanceLibrary.computeNeededClasses(20, 75.0));
        assertEquals(10, attendanceLibrary.computeNeededClasses(20, 50.0));
    }

    @Test
    public void testComputeRemainingClassesToAttend() {
        assertEquals(24, attendanceLibrary.computeRemainingClassesToAttend(34, 10));
        assertEquals(0, attendanceLibrary.computeRemainingClassesToAttend(20, 20));
        assertEquals(5, attendanceLibrary.computeRemainingClassesToAttend(15, 10));
    }
}
