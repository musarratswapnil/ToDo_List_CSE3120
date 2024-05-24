package com.example.todo_list.StopWatch;

/**
 * This class provides utility methods for timer calculations and formatting.
 */
public class TimerLogic {

    /**
     * Calculates the total duration in milliseconds based on the given hours, minutes, and seconds.
     *
     * @param hours The number of hours.
     * @param minutes The number of minutes.
     * @param seconds The number of seconds.
     * @return The total duration in milliseconds.
     */
    public static long calculateTimerDuration(long hours, long minutes, long seconds) {
        long totalSeconds = hours * 3600 + minutes * 60 + seconds;
        return totalSeconds * 1000; // Convert to milliseconds
    }

    /**
     * Formats the given time in milliseconds to an array of strings representing hours, minutes, and seconds.
     *
     * @param millis The time in milliseconds.
     * @return An array of strings in the format {"HH:", "MM:", "SS"}.
     */
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

    /**
     * Determines the color for the timer based on the remaining time.
     *
     * @param millis The remaining time in milliseconds.
     * @param threshold The threshold time in milliseconds.
     * @param colorRed The color to use if the remaining time is below the threshold.
     * @param colorGray The color to use if the remaining time is above the threshold.
     * @return The color to use for the timer.
     */
    public static int getColorForTime(long millis, long threshold, int colorRed, int colorGray) {
        return millis < threshold ? colorRed : colorGray;
    }
}
