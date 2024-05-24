package com.example.todo_list.AttendanceCalculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AttendancePercentageLogicTest {

    // Stub class for AttendanceAdapter
    class AttendanceAdapterStub extends AttendanceAdapter {
        @Override
        public int calculateTotalClasses(int credits, int weeks) {
            return credits * weeks; // Simplified logic for testing
        }

        @Override
        public double calculateAttendancePercentage(int attended, int total) {
            return (double) attended / total * 100;
        }
    }

    @Test
    public void testCalculatePercentage() {
        AttendanceAdapter attendanceAdapter = new AttendanceAdapterStub();
        AttendancePercentageLogic attendancePercentageLogic = new AttendancePercentageLogic(attendanceAdapter);

        // Test case 1
        double percentage = attendancePercentageLogic.calculatePercentage(3, 15, 30);
        assertEquals(66.67, percentage, 0.01);

        // Test case 2
        percentage = attendancePercentageLogic.calculatePercentage(4, 15, 60);
        assertEquals(100.0, percentage, 0.01);

        // Test case 3
        percentage = attendancePercentageLogic.calculatePercentage(2, 15, 10);
        assertEquals(33.33, percentage, 0.01);
    }
}
