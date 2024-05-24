package com.example.todo_list.AttendanceCalculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class AttendanceCalculatorLogicTest {

    @Test
    public void testCalculateTotalClasses() {
        AttendanceCalculatorLogic logic = new AttendanceCalculatorLogic();
        assertEquals(45, logic.calculateTotalClasses(3, 15));
    }

    @Test
    public void testCalculateRemainingClasses() {
        AttendanceCalculatorLogic logic = new AttendanceCalculatorLogic();
        assertEquals(9, logic.calculateRemainingClasses(3, 3));
    }

    @Test
    public void testCalculateNeededClasses() {
        AttendanceCalculatorLogic logic = new AttendanceCalculatorLogic();
        assertEquals(30, logic.calculateNeededClasses(3, 15, 66.67));
    }

    @Test
    public void testCalculateRemainingClassesToAttend() {
        AttendanceCalculatorLogic logic = new AttendanceCalculatorLogic();
        assertEquals(0, logic.calculateRemainingClassesToAttend(3, 15, 30, 66.67));
    }

    @Test
    public void testCanAchieveDesiredPercentage() {
        AttendanceCalculatorLogic logic = new AttendanceCalculatorLogic();
        assertTrue(logic.canAchieveDesiredPercentage(3, 3, 0));
        assertFalse(logic.canAchieveDesiredPercentage(3, 3, 10));
    }
}
