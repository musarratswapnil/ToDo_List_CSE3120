package com.example.todo_list.Note.Sort;

import com.example.todo_list.Note.Listdata;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByNameStrategy implements SortingStrategy {
    @Override
    public void sort(List<Listdata> list) {
        Collections.sort(list, new Comparator<Listdata>() {
            @Override
            public int compare(Listdata o1, Listdata o2) {
                return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
            }
        });
    }
}