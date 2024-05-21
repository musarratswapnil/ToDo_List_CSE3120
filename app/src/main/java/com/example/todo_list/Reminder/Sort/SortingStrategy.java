package com.example.todo_list.Reminder.Sort;

import com.example.todo_list.Note.Listdata;
import com.example.todo_list.Reminder.Task;

import java.util.List;

public interface SortingStrategy {
    void sort(List<Task> list);
}