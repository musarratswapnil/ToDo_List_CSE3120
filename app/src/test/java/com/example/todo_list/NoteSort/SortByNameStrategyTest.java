package com.example.todo_list.NoteSort;


import static org.junit.Assert.assertEquals;

import com.example.todo_list.KeepNote.Listdata;
import com.example.todo_list.KeepNote.Sort.SortByNameStrategy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SortByNameStrategyTest {
    private SortByNameStrategy sortByNameStrategy;

    @Before
    public void setUp() {
        sortByNameStrategy = new SortByNameStrategy();
    }

    @Test
    public void SortName() {
        List<Listdata> list = new ArrayList<>();
        list.add(new Listdata("1", "Banana", "A yellow fruit",1));
        list.add(new Listdata("2", "Apple", "A red or green fruit",1));
        list.add(new Listdata("3", "Grapes", "A small, round, purple or green fruit",1));

        sortByNameStrategy.sort(list);

        assertEquals("Apple", list.get(0).getTitle());
        assertEquals("Banana", list.get(1).getTitle());
        assertEquals("Grapes", list.get(2).getTitle());
    }

    @Test
    public void SortEmptyList() {
        List<Listdata> list = new ArrayList<>();

        sortByNameStrategy.sort(list);

        assertEquals(0, list.size());
    }

    @Test
    public void SortSingleList() {
        List<Listdata> list = new ArrayList<>();
        list.add(new Listdata("1", "Banana", "A yellow fruit",2));

        sortByNameStrategy.sort(list);

        assertEquals(1, list.size());
        assertEquals("Banana", list.get(0).getTitle());
    }

    @Test
    public void SortWithMixedCase() {
        List<Listdata> list = new ArrayList<>();
        list.add(new Listdata("1", "banana", "A yellow fruit",2));
        list.add(new Listdata("2", "Apple", "A red or green fruit",2));
        list.add(new Listdata("3", "grapes", "A small, round, purple or green fruit",2));

        sortByNameStrategy.sort(list);

        assertEquals("Apple", list.get(0).getTitle());
        assertEquals("banana", list.get(1).getTitle());
        assertEquals("grapes", list.get(2).getTitle());
    }
}