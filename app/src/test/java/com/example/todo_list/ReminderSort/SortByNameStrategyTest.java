package com.example.todo_list.ReminderSort;

import static org.junit.Assert.*;

import com.example.todo_list.Reminder.Sort.SortByNameStrategy;
import com.example.todo_list.Reminder.Task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SortByNameStrategyTest {

    @Test
    public void sortNameCheck(){

        List<Task> taskList=new ArrayList<>();

        Task task1=new Task("1","10/05/2024","t1","abcd","15:16");
        Task task2=new Task("2","10/05/2024","shipra","abcd","15:16");
        Task task3=new Task("3","10/05/2024","Note","abcd","15:16");

        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        SortByNameStrategy sorting=new SortByNameStrategy();
        sorting.sort(taskList);

        assertEquals("t1",taskList.get(0).getTitle());
        assertEquals("shipra",taskList.get(1).getTitle());
        assertEquals("Note",taskList.get(2).getTitle());


    }

    @Test
    public void alreadySortedListCheck() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task("1", "10/05/2024", "cTask", "abcd", "15:16"));
        taskList.add(new Task("2", "10/05/2024", "bTask", "abcd", "15:16"));
        taskList.add(new Task("3", "10/05/2024", "aTask", "abcd", "15:16"));

        assertEquals("aTask", taskList.get(2).getTitle());
        assertEquals("bTask", taskList.get(1).getTitle());
        assertEquals("cTask", taskList.get(0).getTitle());

        SortByNameStrategy sorting = new SortByNameStrategy();
        sorting.sort(taskList);

        assertEquals("aTask", taskList.get(2).getTitle());
        assertEquals("bTask", taskList.get(1).getTitle());
        assertEquals("cTask", taskList.get(0).getTitle());
    }


    @Test
    public void duplicateTitlesCheck() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task("1", "10/05/2024", "aTask", "abcd", "15:16"));
        taskList.add(new Task("2", "10/05/2024", "aTask", "abcd", "15:16"));
        taskList.add(new Task("3", "10/05/2024", "bTask", "abcd", "15:16"));

        SortByNameStrategy sorting = new SortByNameStrategy();
        sorting.sort(taskList);

        assertEquals("aTask", taskList.get(2).getTitle());
        assertEquals("aTask", taskList.get(1).getTitle());
        assertEquals("bTask", taskList.get(0).getTitle());
    }

    @Test
    public void sortLargeNumberOfTasks(){
        List<Task> taskList=new ArrayList<>();

        for(int i=0;i<101;i++){
            taskList.add(new Task(String.valueOf(i), "10/05/2024", "Task"+String.valueOf(i), "abcd", "15:16"));
        }

        SortByNameStrategy sorting=new SortByNameStrategy();
        sorting.sort(taskList);

        assertEquals("Task99",taskList.get(0).getTitle());
        assertEquals(101,taskList.size());

    }

    @Test
    public void sortEmptyTask(){
        List<Task> taskList=new ArrayList<>();

        SortByNameStrategy sorting=new SortByNameStrategy();
        sorting.sort(taskList);
        assertTrue(taskList.isEmpty());

    }

    @Test
    public void MixedCaseCheck() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task("1", "10/05/2024", "BTask", "abcd", "15:16"));
        taskList.add(new Task("2", "10/05/2024", "eTask", "abcd", "15:16"));
        taskList.add(new Task("3", "10/05/2024", "aTask", "abcd", "15:16"));

        SortByNameStrategy sorting = new SortByNameStrategy();
        sorting.sort(taskList);

        assertEquals("aTask", taskList.get(2).getTitle());
        assertEquals("BTask", taskList.get(1).getTitle());
        assertEquals("eTask", taskList.get(0).getTitle());
    }



}