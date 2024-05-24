package com.example.todo_list.AttendanceCalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttendanceCalculatorLogicTest {

    private AttendanceCalculatorLogic attendanceCalculatorLogic;
    private AttendanceAdapter attendanceAdapter;

    @Before
    public void setUp() {
        attendanceAdapter = new AttendanceAdapter();
        attendanceCalculatorLogic = new AttendanceCalculatorLogic(attendanceAdapter);
    }

    @Test
    public void testCalculateNeededClasses_NotAchievable() {
        // Simulating external library calculations for the purpose of this test
        // You need to replace the values with actual values based on your calculations
        int totalCredit = 3;
        int totalWeeks = 15;
        int classesAttended = 10;
        double desiredPercentage = 75.0;
        int remainingWeeks = 5;

        int totalClasses = 45; // Simulated value from attendanceAdapter.calculateTotalClasses
        int totalRemainingClasses = 15; // Simulated value from attendanceAdapter.calculateTotalClasses
        int neededClasses = 34; // Simulated value from attendanceAdapter.calculateNeededClasses
        int remainingClassesToAttend = 24; // Simulated value from attendanceAdapter.calculateRemainingClassesToAttend

        // Injecting these simulated values using subclassing for testing
        attendanceAdapter = new AttendanceAdapter() {
            @Override
            public int calculateTotalClasses(int totalCredit, int totalWeeks) {
                if (totalWeeks == 15) {
                    return 45;
                } else if (totalWeeks == 5) {
                    return 15;
                }
                return 0;
            }

            @Override
            public int calculateNeededClasses(int totalClasses, double desiredPercentage) {
                return 34;
            }

            @Override
            public int calculateRemainingClassesToAttend(int neededClasses, int classesAttended) {
                return 24;
            }
        };

        attendanceCalculatorLogic = new AttendanceCalculatorLogic(attendanceAdapter);
        AttendanceCalculatorLogic.CalculationResult result = attendanceCalculatorLogic.calculateNeededClasses(totalCredit, totalWeeks, classesAttended, desiredPercentage, remainingWeeks);

        assertEquals(15, result.getTotalRemainingClasses());
        assertEquals("24", result.getRemainingClassesToAttend());
        assertEquals("Sorry, you can't achieve 75.00% attendance in 5 weeks.", result.getMessage());
    }

    @Test
    public void testCalculateNeededClasses_NeedsMoreClasses() {
        // Similar setup as above test
        attendanceAdapter = new AttendanceAdapter() {
            @Override
            public int calculateTotalClasses(int totalCredit, int totalWeeks) {
                if (totalWeeks == 15) {
                    return 45;
                } else if (totalWeeks == 5) {
                    return 15;
                }
                return 0;
            }

            @Override
            public int calculateNeededClasses(int totalClasses, double desiredPercentage) {
                return 34;
            }

            @Override
            public int calculateRemainingClassesToAttend(int neededClasses, int classesAttended) {
                return 14;
            }
        };

        attendanceCalculatorLogic = new AttendanceCalculatorLogic(attendanceAdapter);
        AttendanceCalculatorLogic.CalculationResult result = attendanceCalculatorLogic.calculateNeededClasses(3, 15, 20, 75.0, 5);

        assertEquals(15, result.getTotalRemainingClasses());
        assertEquals("14", result.getRemainingClassesToAttend());
        assertEquals("You need to attend 14 more classes to achieve 75.00% attendance.", result.getMessage());
    }

    @Test
    public void testCalculateNeededClasses_AlreadyAchieved() {
        // Similar setup as above tests
        attendanceAdapter = new AttendanceAdapter() {
            @Override
            public int calculateTotalClasses(int totalCredit, int totalWeeks) {
                if (totalWeeks == 15) {
                    return 45;
                } else if (totalWeeks == 5) {
                    return 15;
                }
                return 0;
            }

            @Override
            public int calculateNeededClasses(int totalClasses, double desiredPercentage) {
                return 34;
            }

            @Override
            public int calculateRemainingClassesToAttend(int neededClasses, int classesAttended) {
                return 0;
            }
        };

        attendanceCalculatorLogic = new AttendanceCalculatorLogic(attendanceAdapter);
        AttendanceCalculatorLogic.CalculationResult result = attendanceCalculatorLogic.calculateNeededClasses(3, 15, 35, 75.0, 5);

        assertEquals(15, result.getTotalRemainingClasses());
        assertEquals("0", result.getRemainingClassesToAttend());
        assertEquals("You have already achieved 75.00% attendance and attended 1 more classes than required.", result.getMessage());
    }
}
