package com.example.todo_list.Reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class TaskTest {

    private Task task;

    @Before
    public void setUp() {
        task = new Task("key1", "27/05/2024", "Title", "Content", "14:00",993498369);
    }

    @Test
    public void testGetDay() {
        assertEquals(29, task.getDay());
    }

    @Test
    public void testGetMonth() {
        assertEquals(4, task.getMonth());
    }

    @Test
    public void testGetYear() {
        assertEquals(2024, task.getYear());
    }

    @Test
    public void testIsOverdue() {
        Task pastTask = new Task("key2", "26/05/2023", "Past Task", "Past Content", "14:00",993498369);
        assertTrue(pastTask.isOverdue());

        Task futureTask = new Task("key3", "28/05/2025", "Future Task", "Future Content", "14:00",993498369);
        assertFalse(futureTask.isOverdue());
    }

    @Test
    public void testNoArgConstructor() {
        Task emptyTask = new Task();
        assertNull(emptyTask.getKey());
        assertNull(emptyTask.getDate());
        assertNull(emptyTask.getTitle());
        assertNull(emptyTask.getContent());
        assertNull(emptyTask.getTime());
    }

    // Corner case: Leap year
    @Test
    public void testLeapYearDate() {
        Task leapYearTask = new Task("key4", "03/02/2024", "Leap Year Task", "Leap Year Content", "12:00",993498369);
        assertEquals(3, leapYearTask.getDay());
//        assertEquals(2, leapYearTask.getMonth());
//        assertEquals(2024, leapYearTask.getYear());
    }



    // Corner case: Null date
    @Test
    public void testNullDate() {
        Task nullDateTask = new Task("key6", null, "Null Date Task", "Null Date Content", "12:00",993498369);
        Calendar calendar = nullDateTask.getParsedDate();
        Calendar current = Calendar.getInstance();
        assertEquals(current.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(current.get(Calendar.MONTH), calendar.get(Calendar.MONTH));
        assertEquals(current.get(Calendar.YEAR), calendar.get(Calendar.YEAR));
    }

    // Corner case: Different date format
    @Test
    public void testDifferentDateFormat() {
        // Assuming date format is strictly "dd/MM/yyyy"
        Task differentFormatTask = new Task("key7", "2024-05-27", "Different Format Task", "Different Format Content", "12:00",993498369);
        Calendar calendar = differentFormatTask.getParsedDate();
        Calendar current = Calendar.getInstance();
        assertEquals(current.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(current.get(Calendar.MONTH), calendar.get(Calendar.MONTH));
        assertEquals(current.get(Calendar.YEAR), calendar.get(Calendar.YEAR));
    }

    // Corner case: Null time
    @Test
    public void testNullTime() {
        Task nullTimeTask = new Task("key8", "27/05/2024", "Null Time Task", "Null Time Content", null,993498369);
        assertFalse(nullTimeTask.isOverdue());  // As no time is specified, it should not be overdue
    }

    // Corner case: Invalid time
    @Test
    public void testInvalidTime() {
        Task invalidTimeTask = new Task("key9", "27/05/2024", "Invalid Time Task", "Invalid Time Content", "25:00",993498369);
        assertFalse(invalidTimeTask.isOverdue());  // As the time is invalid, it should not be overdue
    }



}