package com.example.todo_list.AttendanceCalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttendanceAdapterTest {

    private AttendanceAdapter attendanceAdapter;
    private ExternalAttendanceLibrary externalLibrary;

    @Before
    public void setUp() {
        externalLibrary = new ExternalAttendanceLibrary();
        attendanceAdapter = new AttendanceAdapter(externalLibrary);
    }

    @Test
    public void testCalculateTotalClasses() {
        int totalClasses = attendanceAdapter.calculateTotalClasses(3, 15);

        // Replace with actual expected value based on ExternalAttendanceLibrary's implementation
        int expectedTotalClasses = 45; // Assumed value for the sake of example
        assertEquals(expectedTotalClasses, totalClasses);
    }

    @Test
    public void testCalculateAttendancePercentage() {
        double attendancePercentage = attendanceAdapter.calculateAttendancePercentage(10, 45);

        // Replace with actual expected value based on ExternalAttendanceLibrary's implementation
        double expectedAttendancePercentage = 22.22; // Assumed value for the sake of example
        assertEquals(expectedAttendancePercentage, attendancePercentage, 0.01);
    }

    @Test
    public void testCalculateNeededClasses() {
        int neededClasses = attendanceAdapter.calculateNeededClasses(45, 75.0);

        // Replace with actual expected value based on ExternalAttendanceLibrary's implementation
        int expectedNeededClasses = 34; // Assumed value for the sake of example
        assertEquals(expectedNeededClasses, neededClasses);
    }

    @Test
    public void testCalculateRemainingClassesToAttend() {
        int remainingClassesToAttend = attendanceAdapter.calculateRemainingClassesToAttend(34, 10);

        // Replace with actual expected value based on ExternalAttendanceLibrary's implementation
        int expectedRemainingClassesToAttend = 24; // Assumed value for the sake of example
        assertEquals(expectedRemainingClassesToAttend, remainingClassesToAttend);
    }
}
