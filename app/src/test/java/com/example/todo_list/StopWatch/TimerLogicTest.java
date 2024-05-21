package com.example.todo_list.StopWatch;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;


public class TimerLogicTest {

    @Test
    public void testCalculateTimerDurationForZeroInput() {
        assertEquals("Duration should be 0 for all zero inputs", 0, TimerLogic.calculateTimerDuration(0, 0, 0));
    }

    @Test
    public void testCalculateTimerDurationForOneSecond() {
        assertEquals("Duration should be 1000 milliseconds for one second", 1000, TimerLogic.calculateTimerDuration(0, 0, 1));
    }

    @Test
    public void testCalculateTimerDurationForOneMinute() {
        assertEquals("Duration should be 60000 milliseconds for one minute", 60000, TimerLogic.calculateTimerDuration(0, 1, 0));
    }

    @Test
    public void testCalculateTimerDurationForOneHour() {
        assertEquals("Duration should be 3600000 milliseconds for one hour", 3600000, TimerLogic.calculateTimerDuration(1, 0, 0));
    }

    @Test
    public void testCalculateTimerDurationForComplexTime() {
        assertEquals("Duration should be 3661000 milliseconds for 1 hour, 1 minute, 1 second", 3661000, TimerLogic.calculateTimerDuration(1, 1, 1));
    }

    @Test
    public void testFormatTime() {
        String[] formattedTime = TimerLogic.formatTime(3661000); // 1 hour, 1 minute, 1 second
        assertArrayEquals(new String[] {"01:", "01:", "01"}, formattedTime);
    }



    @Test
    public void testGetColorForTime() {
        int redColor = 0xFF0000; // Example color code for red
        int grayColor = 0x808080; // Example color code for gray
        long threshold = 10000; // 10 seconds threshold

        // Time below threshold
        assertEquals(redColor, TimerLogic.getColorForTime(9000, threshold, redColor, grayColor));

        // Time above threshold
        assertEquals(grayColor, TimerLogic.getColorForTime(11000, threshold, redColor, grayColor));
    }
}


