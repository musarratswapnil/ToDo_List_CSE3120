package com.example.todo_list.KeepNote.Sort;

import com.example.todo_list.KeepNote.Listdata;

import java.util.List;

public interface SortingStrategy {
    void sort(List<Listdata> list);
}