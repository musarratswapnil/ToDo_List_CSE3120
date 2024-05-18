package com.example.todo_list.StopWatch;

public class StopwatchLogic {
    public static final long TIMER_HAS_NOT_STARTED_YET = -1;
    public static final long LONG_DURATION_FOR_TIMER = 3_660_099; // milliseconds equal to 1 hour, 1 minute, 0.99 seconds

    public static long calculateRemainingTime(long duration, long remainingTime) {
        return duration - remainingTime;
    }

    public static String formatTime(long mSeconds) {
        long totalSeconds = mSeconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        long tenMilliSeconds = (mSeconds % 1000) / 10;
        return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, tenMilliSeconds);
    }
}
