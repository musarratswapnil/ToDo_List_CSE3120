package com.example.todo_list.ReminderSort;

import static org.junit.Assert.*;

import com.example.todo_list.Reminder.Sort.SortByDateStrategy;
import com.example.todo_list.Reminder.Task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * This test class is for {@link SortByDateStrategy}.
 * This class contains tests to verify that the SortByDateStrategy correctly sorts a list of {@link Task} objects.
 */
public class SortByDateStrategyTest {
    /**
     * Tests the sorting of tasks by their date and time.Here actually sorted by descending order
     * like 10/5/2024 will be at 1st position and 9/05/24 will be at 2nd position
     * It creates a list of tasks with different dates and adds them to a list.
     * After sorting the list using {@link SortByDateStrategy}, it verifies that the tasks are sorted.
     *
     * <p>Expected order based on dates:</p>
     * <ul>
     * <li>Task with date "12/05/2024" should come first.</li>
     * <li>Task with date "10/05/2024" should come second.</li>
     * <li>Task with date "9/05/2024" should come last.</li>
     * </ul>
     * The test checks if the order of the tasks in the list matches the expected order after sorting.
     */
    @Test
    public void sortTaskBydateandTime(){
        Task task1=new Task("1","10/05/2024","t1","abcd","15:16",993498369);
        Task task2=new Task("2","12/05/2024","t2","abcd","15:20",993498369);
        Task task3=new Task("3","9/05/2024","t3","abcd","14:16",993498369);

        List<Task> tasks=new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        SortByDateStrategy sorting=new SortByDateStrategy();
        sorting.sort(tasks);

        assertEquals("t2",tasks.get(0).getTitle());
        assertEquals("t1",tasks.get(1).getTitle());
        assertEquals("t3",tasks.get(2).getTitle());




    }

    /**
     * Tests the sorting of tasks that have the same date but different times.
     * The tasks are sorted in ascending order based on their time for the same date.
     * This test verifies the ability of {@link SortByDateStrategy} to correctly sort tasks by time when the dates are identical.
     *
     * <p>Expected order based on time:</p>
     * <ul>
     * <li>Task with time "14:16" on "10/05/2024" should come first.</li>
     * <li>Task with time "15:16" on "10/05/2024" should come second.</li>
     * <li>Task with time "15:20" on "10/05/2024" should come last.</li>
     * </ul>
     * The test checks if the order of the tasks in the list matches the expected order after sorting by time on the same date.
     */
    @Test
    public void sortTaskBySamedateandDiffTime(){
        //same date different time
        Task task1=new Task("1","10/05/2024","t1","abcd","15:16",993498369);
        Task task2=new Task("2","10/05/2024","t2","abcd","15:20",993498369);
        Task task3=new Task("3","10/05/2024","t3","abcd","14:16",993498369);

        List<Task> tasks=new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        SortByDateStrategy sorting=new SortByDateStrategy();
        sorting.sort(tasks);

        assertEquals("t2",tasks.get(0).getTitle());
        assertEquals("t1",tasks.get(1).getTitle());
        assertEquals("t3",tasks.get(2).getTitle());
    }

    @Test
    public void sortIdenticalDatesAndTimes(){
        Task task1=new Task("1","10/05/2024","t1","abcd","15:16",993498369);
        Task task2=new Task("2","10/05/2024","t2","abcd","15:16",993498369);
        Task task3=new Task("3","10/05/2024","t3","abcd","15:16",993498369);

        List<Task> tasks=new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        SortByDateStrategy sorting=new SortByDateStrategy();
        sorting.sort(tasks);

        assertTrue(tasks.indexOf(task1)!=-1);
        assertTrue(tasks.indexOf(task2)!=-1);
        assertTrue(tasks.indexOf(task3)!=-1);
        assertTrue(tasks.indexOf(task1)!=3);
    }

    @Test
    public void sortSingleElementList(){
       Task task1=new Task("2","10/05/2024","t1","Content","12:02",993498369);
       List<Task> tasks=new ArrayList<>();
       tasks.add(task1);

        SortByDateStrategy sorting=new SortByDateStrategy();
        sorting.sort(tasks);

       assertEquals(1,tasks.size());
       assertEquals("12:02",tasks.get(0).getTime());
        assertNotEquals("12/02",tasks.get(0).getTime());


    }

    @Test
    public void sortEmptyListByDate() {
        List<Task> tasks = new ArrayList<>();
        SortByDateStrategy sorting=new SortByDateStrategy();
        sorting.sort(tasks);
        assertTrue(tasks.isEmpty());
    }

    /*@Test
    public void SortwithNullDate(){
        Task task1=new Task("1",null,"t1","abcd","15:16");
        Task task2=new Task("2","10/05/2024","t2","abcd","15:16");
        Task task3=new Task("3","10/05/2024","t3","abcd","15:16");

        List<Task> tasks=new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        SortByDateStrategy sorting=new SortByDateStrategy();
        sorting.sort(tasks);

        assertEquals("t2",tasks.get(0).getTitle());
        assertEquals("t1",tasks.get(2).getTitle());
        assertEquals("t3",tasks.get(1).getTitle());
    }*/

    @Test
    public void sortLargeNumberOfTasks() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1000; i > 0; i--) {
            tasks.add(new Task(String.valueOf(i), "10/05/" + (2024 + i), "t" + i, "Content " + i, "15:" + (i % 60),993498369));
        }

        SortByDateStrategy sorting=new SortByDateStrategy();
        sorting.sort(tasks);

        assertEquals("t1000", tasks.get(0).getTitle());
        assertEquals("t1", tasks.get(tasks.size() - 1).getTitle());
    }

    }