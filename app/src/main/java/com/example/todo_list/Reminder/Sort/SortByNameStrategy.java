package com.example.todo_list.Reminder.Sort;

import com.example.todo_list.Reminder.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByNameStrategy implements SortingStrategy {
    @Override
    public void sort(List<Task> list) {
        Collections.sort(list, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return task2.getTitle().toLowerCase().compareTo(task1.getTitle().toLowerCase());
            }
        });
    }
}
