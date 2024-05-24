package com.example.todo_list.StopWatch;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StopwatchLogicTest {

    private StopwatchLogic stopwatchLogic;

    @Before
    public void setUp() {
        stopwatchLogic = StopwatchLogic.getInstance();
    }

    @Test
    public void testCalculateRemainingTime() {
        long duration = 10000;
        long remainingTime = 5000;
        long expectedRemainingTime = duration - remainingTime;
        assertEquals(expectedRemainingTime, stopwatchLogic.calculateRemainingTime(duration, remainingTime));
    }

    @Test
    public void testFormatTime() {
        long millis = 3660990; // 1 hour, 1 minute, 0.99 seconds
        String expectedFormattedTime = "01:01:00:99";
        assertEquals(expectedFormattedTime, stopwatchLogic.formatTime(millis));
    }
}
