package com.example.todo_list.StopWatch;

/**
 * This class provides utility methods for timer calculations and formatting.
 */
public class TimerLogic {

    /**
     * Calculates the duration of the timer in milliseconds.
     *
     * @param hours   The number of hours in the timer.
     * @param minutes The number of minutes in the timer.
     * @param seconds The number of seconds in the timer.
     * @return The duration of the timer in milliseconds.
     */
    public long calculateTimerDuration(long hours, long minutes, long seconds) {
        long totalSeconds = hours * 3600 + minutes * 60 + seconds;
        return totalSeconds * 1000; // Convert to milliseconds
    }

    /**
     * Formats the time in milliseconds into hours, minutes, and seconds.
     *
     * @param millis The time in milliseconds to format.
     * @return An array of strings representing formatted time components [hours, minutes, seconds].
     */
    public String[] formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        return new String[]{
                String.format("%02d:", hours),
                String.format("%02d:", minutes),
                String.format("%02d", seconds)
        };
    }

    /**
     * Gets the color code based on the time threshold.
     *
     * @param millis       The time in milliseconds.
     * @param threshold    The threshold time in milliseconds.
     * @param colorRed     The color code for time below the threshold.
     * @param colorGray    The color code for time equal to or above the threshold.
     * @return The color code based on the time threshold.
     */
    public int getColorForTime(long millis, long threshold, int colorRed, int colorGray) {
        return millis < threshold ? colorRed : colorGray;
    }
}
