package com.example.todo_list.StopWatch;

/**
 * This class contains the logic for the stopwatch.
 * It provides methods to calculate remaining time and format time for display.
 */
public class StopwatchLogic {

    /**
     * Constant representing the state when the timer has not started yet.
     */
    public static final long TIMER_HAS_NOT_STARTED_YET = -1;

    /**
     * Constant representing a long duration for the timer (1 hour, 1 minute, 0.99 seconds).
     */
    public static final long LONG_DURATION_FOR_TIMER = 3_660_099; // milliseconds equal to 1 hour, 1 minute, 0.99 seconds

    /**
     * Calculates the remaining time.
     *
     * @param duration The total duration.
     * @param remainingTime The time that has already passed.
     * @return The remaining time.
     */
    public static long calculateRemainingTime(long duration, long remainingTime) {
        return duration - remainingTime;
    }

    /**
     * Formats the time in milliseconds to a string in the format HH:mm:ss:SS.
     *
     * @param mSeconds The time in milliseconds.
     * @return The formatted time string.
     */
    public static String formatTime(long mSeconds) {
        long totalSeconds = mSeconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        long tenMilliSeconds = (mSeconds % 1000) / 10;
        return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, tenMilliSeconds);
    }
}
