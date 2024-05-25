package com.example.todo_list.App_Options.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelpAdapterTest {
    private HelpAdapter adapter;
    private List<Help> helpList;

    @Before
    public void setUp() {
        helpList = new ArrayList<>();
        helpList.add(new Help("How to add a task?", "Open the app and tap on add."));
        helpList.add(new Help("How to delete a task?", "Swipe the task to the left."));
        adapter = new HelpAdapter(helpList);
    }

    @Test
    public void itemCount_correct() {
        // Test to check if getItemCount returns the correct size of the list
        assertEquals("Item count should match the size of helpList", 2, adapter.getItemCount());
    }
}
