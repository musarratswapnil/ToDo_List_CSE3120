package com.example.todo_list.StopWatch;

/**
 * This class contains the logic for the stopwatch.
 * It provides methods to calculate remaining time and format time for display.
 */
public class StopwatchLogic {

    // Constructor can be public or package-private
    public StopwatchLogic() {}

    // Constants
    /**
     * Constant representing that the timer has not started yet.
     */
    public static final long TIMER_HAS_NOT_STARTED_YET = -1;

    /**
     * Long duration for the timer (1 hour, 1 minute, 0.99 seconds) in milliseconds.
     */
    public static final long LONG_DURATION_FOR_TIMER = 3_660_099;

    /**
     * Calculates the remaining time for the stopwatch.
     *
     * @param duration      The total duration of the stopwatch.
     * @param remainingTime The remaining time of the stopwatch.
     * @return The calculated remaining time in milliseconds.
     */
    public long calculateRemainingTime(long duration, long remainingTime) {
        return duration - remainingTime;
    }

    /**
     * Formats the time for display.
     *
     * @param mSeconds The time in milliseconds to be formatted.
     * @return The formatted time string in the format "HH:MM:SS:SS".
     */
    public String formatTime(long mSeconds) {
        long totalSeconds = mSeconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        long tenMilliSeconds = (mSeconds % 1000) / 10;
        return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, tenMilliSeconds);
    }
}
