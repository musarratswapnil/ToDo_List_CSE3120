package com.example.todo_list.StopWatch;

// File: TimerUtils.java


public class TimerLogic {

    public static long calculateTimerDuration(long hours, long minutes, long seconds) {
        long totalSeconds = hours * 3600 + minutes * 60 + seconds;
        return totalSeconds * 1000; // Convert to milliseconds
    }

    public static String[] formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        return new String[] {
                String.format("%02d:", hours),
                String.format("%02d:", minutes),
                String.format("%02d", seconds)
        };
    }

    public static int getColorForTime(long millis, long threshold, int colorRed, int colorGray) {
        return millis < threshold ? colorRed : colorGray;
    }
}

