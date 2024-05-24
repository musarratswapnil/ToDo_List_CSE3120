package com.example.todo_list.AttendanceCalculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AttendancePercentageLogicTest {

    @Test
    public void testCalculatePercentage() {
        AttendancePercentageLogic logic = new AttendancePercentageLogic();
        double percentage = logic.calculatePercentage(3, 15, 30);
        assertEquals(66.67, percentage, 0.01);
    }
}
