package com.example.todo_list.Note.Sort;

import com.example.todo_list.Note.Listdata;

import java.util.List;

public interface SortingStrategy {
    void sort(List<Listdata> list);
}