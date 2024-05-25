package com.example.todo_list.StopWatch;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TimerLogicTest {

    private TimerLogic timerLogic;

    @Before
    public void setUp() {
        timerLogic = TimerLogic.getInstance();
    }

    @Test
    public void testCalculateTimerDuration() {
        long hours = 1;
        long minutes = 1;
        long seconds = 1;
        long expectedDuration = (hours * 3600 + minutes * 60 + seconds) * 1000;
        assertEquals(expectedDuration, timerLogic.calculateTimerDuration(hours, minutes, seconds));
    }

    @Test
    public void testFormatTime() {
        long millis = 3661000; // 1 hour, 1 minute, 1 second
        String[] expectedTime = {"01:", "01:", "01"};
        assertArrayEquals(expectedTime, timerLogic.formatTime(millis));
    }

    @Test
    public void testGetColorForTime() {
        long millis = 5000;
        long threshold = 10000;
        int colorRed = 0xFF0000;
        int colorGray = 0x808080;

        assertEquals(colorRed, timerLogic.getColorForTime(millis, threshold, colorRed, colorGray));
        assertEquals(colorGray, timerLogic.getColorForTime(threshold + 1, threshold, colorRed, colorGray));
    }
}
