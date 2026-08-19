package com.example.todo_list.StopWatch;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TimerLogicTest {

    @Test
    public void testCalculateTimerDuration() {
        TimerLogic timerLogic = new TimerLogic();
        long duration = timerLogic.calculateTimerDuration(1, 30, 45);
        assertEquals(5445000, duration); // 1 hour, 30 minutes, 45 seconds in milliseconds
    }

    @Test
    public void testFormatTime() {
        TimerLogic timerLogic = new TimerLogic();
        String[] formattedTime = timerLogic.formatTime(5445000);
        assertEquals("01:", formattedTime[0]);
        assertEquals("30:", formattedTime[1]);
        assertEquals("45", formattedTime[2]);
    }

    @Test
    public void testGetColorForTime() {
        TimerLogic timerLogic = new TimerLogic();
        int colorRed = 0xFF0000;
        int colorGray = 0x808080;
        assertEquals(colorRed, timerLogic.getColorForTime(5000, 10000, colorRed, colorGray));
        assertEquals(colorGray, timerLogic.getColorForTime(15000, 10000, colorRed, colorGray));
    }
}
