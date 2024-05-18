package com.example.todo_list.StopWatch;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StopwatchLogicTest {

    @Test
    public void testCalculateRemainingTime() {
        long duration = StopwatchLogic.LONG_DURATION_FOR_TIMER;
        long remainingTime = 60000; // 60 seconds (1 minute) in milliseconds
        long expectedRemainingMillis = duration - remainingTime; // Expected remaining time in milliseconds
        long remainingMillis = StopwatchLogic.calculateRemainingTime(duration, remainingTime);
        assertEquals(expectedRemainingMillis, remainingMillis);
    }

    @Test
    public void testCalculateRemainingTime_Halfway() {
        long duration = StopwatchLogic.LONG_DURATION_FOR_TIMER;
        long remainingTime = duration / 2; // Halfway through the duration
        long expectedRemainingMillis = duration - remainingTime; // Expected remaining time in milliseconds
        long remainingMillis = StopwatchLogic.calculateRemainingTime(duration, remainingTime);
        assertEquals(expectedRemainingMillis, remainingMillis);
    }

    @Test
    public void testFormatTime() {
        long mSeconds = 3661000; // 1 hour, 1 minute, 1 second in milliseconds
        String formattedTime = StopwatchLogic.formatTime(mSeconds);
        assertEquals("01:01:01:00", formattedTime);
    }

    @Test
    public void testFormatTime_Complex() {
        long mSeconds = 366100; // 6 minutes, 6.1 seconds in milliseconds
        String formattedTime = StopwatchLogic.formatTime(mSeconds);
        assertEquals("00:06:06:10", formattedTime);
    }

    @Test
    public void testFormatTime_Hours() {
        long mSeconds = 7200000; // 2 hours in milliseconds
        String formattedTime = StopwatchLogic.formatTime(mSeconds);
        assertEquals("02:00:00:00", formattedTime);
    }

    @Test
    public void testFormatTime_MinutesAndSeconds() {
        long mSeconds = 90000; // 1 minute and 30 seconds in milliseconds
        String formattedTime = StopwatchLogic.formatTime(mSeconds);
        assertEquals("00:01:30:00", formattedTime);
    }
}
