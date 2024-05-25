package com.example.todo_list.StopWatch;

/**
 * This class provides utility methods for timer calculations and formatting.
 */
public class TimerLogic {

    // Single instance of TimerLogic
    private static TimerLogic instance;

    // Private constructor to prevent instantiation
    private TimerLogic() {}

    // Method to get the single instance of TimerLogic
    public static TimerLogic getInstance() {
        if (instance == null) {
            synchronized (TimerLogic.class) {
                if (instance == null) {
                    instance = new TimerLogic();
                }
            }
        }
        return instance;
    }

    // Existing methods...
    public long calculateTimerDuration(long hours, long minutes, long seconds) {
        long totalSeconds = hours * 3600 + minutes * 60 + seconds;
        return totalSeconds * 1000; // Convert to milliseconds
    }

    public String[] formatTime(long millis) {
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

    public int getColorForTime(long millis, long threshold, int colorRed, int colorGray) {
        return millis < threshold ? colorRed : colorGray;
    }
}
