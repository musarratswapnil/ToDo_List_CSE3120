package com.example.todo_list.AttendanceCalculator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AttendanceAdapterTest {

    private AttendanceAdapter attendanceAdapter;
    private AttendanceCalculatorLogic calculatorLogic;
    private AttendancePercentageLogic percentageLogic;

    @Before
    public void setUp() {
        attendanceAdapter = new AttendanceAdapter();
        calculatorLogic = new AttendanceCalculatorLogic();
        percentageLogic = new AttendancePercentageLogic();
    }

    @Test
    public void testGetAttendanceResult() {
        int totalCredit = 3;
        int totalWeeks = 15;
        int classesAttended = 10;
        double desiredPercentage = 75;
        int remainingWeeks = 5;

        String result = attendanceAdapter.getAttendanceResult(totalCredit, totalWeeks, classesAttended, desiredPercentage, remainingWeeks, calculatorLogic);
        String expected = "Sorry, you can't achieve 75.00% attendance in 5 weeks.";

        assertEquals(expected, result);

        // Test case where attendance can be achieved
        classesAttended = 10;
        remainingWeeks = 10;
        result = attendanceAdapter.getAttendanceResult(totalCredit, totalWeeks, classesAttended, desiredPercentage, remainingWeeks, calculatorLogic);
        expected = "You need to attend 23 more classes to achieve 75.00% attendance.";

        assertEquals(expected, result);

        // Test case where desired percentage is already achieved
        classesAttended = 35;
        result = attendanceAdapter.getAttendanceResult(totalCredit, totalWeeks, classesAttended, desiredPercentage, remainingWeeks, calculatorLogic);
        expected = "You have already achieved 75.00% attendance and attended 2 more classes than required.";

        assertEquals(expected, result);
    }

    @Test
    public void testGetTotalRemainingClasses() {
        int totalCredit = 3;
        int remainingWeeks = 5;

        int totalRemainingClasses = attendanceAdapter.getTotalRemainingClasses(totalCredit, remainingWeeks, calculatorLogic);
        assertEquals(15, totalRemainingClasses);
    }

    @Test
    public void testGetRemainingClassesToAttend() {
        int totalCredit = 3;
        int totalWeeks = 15;
        int classesAttended = 10;
        double desiredPercentage = 75;

        int remainingClassesToAttend = attendanceAdapter.getRemainingClassesToAttend(totalCredit, totalWeeks, classesAttended, desiredPercentage, calculatorLogic);
        assertEquals(23, remainingClassesToAttend);
    }

    @Test
    public void testCalculateAttendancePercentage() {
        int totalCredit = 3;
        int totalWeeks = 15;
        int classesAttended = 30;

        double attendancePercentage = attendanceAdapter.calculateAttendancePercentage(totalCredit, totalWeeks, classesAttended, percentageLogic);
        assertEquals(66.67, attendancePercentage, 0.01);
    }
}
