package com.example.todo_list.StopWatch;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StopwatchLogicTest {

    @Test
    public void testCalculateRemainingTime() {
        StopwatchLogic stopwatchLogic = new StopwatchLogic();
        long duration = 3660099; // 1 hour, 1 minute, 0.99 seconds in milliseconds
        long remainingTime = 66099; // 1 minute, 0.99 seconds in milliseconds
        long calculatedTime = stopwatchLogic.calculateRemainingTime(duration, remainingTime);
        assertEquals(3594000, calculatedTime); // Expected remaining time in milliseconds
    }

//    @Test
//    public void testFormatTime() {
//        StopwatchLogic stopwatchLogic = new StopwatchLogic();
//        String formattedTime = stopwatchLogic.formatTime(3660099);
//        assertEquals("01:01:00:99", formattedTime);
//    }

    @Test
    public void testFormatTimeZero() {
        StopwatchLogic stopwatchLogic = new StopwatchLogic();
        String formattedTime = stopwatchLogic.formatTime(0);
        assertEquals("00:00:00:00", formattedTime);
    }

    @Test
    public void testFormatTimePartial() {
        StopwatchLogic stopwatchLogic = new StopwatchLogic();
        String formattedTime = stopwatchLogic.formatTime(65099); // 1 minute, 5.099 seconds
        assertEquals("00:01:05:09", formattedTime);
    }
}
