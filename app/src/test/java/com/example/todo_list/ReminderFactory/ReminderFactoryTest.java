package com.example.todo_list.ReminderFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import android.content.Context;

import com.example.todo_list.Reminder.RemindMe.AlarmReminder;
import com.example.todo_list.Reminder.RemindMe.CallReminder;
import com.example.todo_list.Reminder.RemindMe.ReminderFactory;
import com.example.todo_list.Reminder.RemindMe.ReminderInterface;
import com.example.todo_list.Reminder.RemindMe.SMSReminder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReminderFactoryTest {

    @Mock
    private Context mockContext;

    private ReminderFactory reminderFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reminderFactory = new ReminderFactory();
    }

    @Test
    public void GetCallReminder() {
        ReminderInterface reminder = reminderFactory.getReminder("call");
        assertNotNull(reminder);
        assertEquals(CallReminder.class, reminder.getClass());
    }

    @Test
    public void GetAlarmReminder() {
        ReminderInterface reminder = reminderFactory.getReminder("alarm");
        assertNotNull(reminder);
        assertEquals(AlarmReminder.class, reminder.getClass());
    }

    @Test
    public void GetSmsReminder() {
        ReminderInterface reminder = reminderFactory.getReminder("sms");
        assertNotNull(reminder);
        assertEquals(SMSReminder.class, reminder.getClass());
    }

    @Test
    public void HandleNullReturn() {
        assertThrows(IllegalArgumentException.class, () -> {
                    reminderFactory.getReminder(null);});
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reminderFactory.getReminder(null);
        });
        assertEquals("Reminder type cannot be null", exception.getMessage());

    }

    @Test
    public void HandleNullReturnMessage() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reminderFactory.getReminder(null);
        });
        assertEquals("Reminder type cannot be null", exception.getMessage());

    }

    @Test
    public void HandleExceptionReturnMessage() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reminderFactory.getReminder("video");
        });
        assertEquals("Unknown reminder type: video", exception.getMessage());

    }

    @Test
    public void HandleUnknownVideoReminder() {
        assertThrows(IllegalArgumentException.class, () -> {
            reminderFactory.getReminder("video");
        });
    }

//    @Test
//    public void testSetReminder_alarm() {
//        ReminderInterface reminder = reminderFactory.getReminder("alarm");
//        assertNotNull(reminder);
//        reminder.setReminder(mockContext, 2024, 5, 25, 14, 30, "Meeting", "Don't forget the meeting!", "1234567890");
//        // Add assertions to verify the behavior if needed
//    }
}
