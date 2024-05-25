package com.example.todo_list.StopWatch;

/**
 * This class contains the logic for the stopwatch.
 * It provides methods to calculate remaining time and format time for display.
 */
public class StopwatchLogic {

    // Single instance of StopwatchLogic
    private static StopwatchLogic instance;

    // Private constructor to prevent instantiation
    private StopwatchLogic() {}

    // Method to get the single instance of StopwatchLogic
    public static StopwatchLogic getInstance() {
        if (instance == null) {
            synchronized (StopwatchLogic.class) {
                if (instance == null) {
                    instance = new StopwatchLogic();
                }
            }
        }
        return instance;
    }

    // Existing methods...
    public static final long TIMER_HAS_NOT_STARTED_YET = -1;
    public static final long LONG_DURATION_FOR_TIMER = 3_660_099; // milliseconds equal to 1 hour, 1 minute, 0.99 seconds

    public long calculateRemainingTime(long duration, long remainingTime) {
        return duration - remainingTime;
    }

    public String formatTime(long mSeconds) {
        long totalSeconds = mSeconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        long tenMilliSeconds = (mSeconds % 1000) / 10;
        return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, tenMilliSeconds);
    }
}
