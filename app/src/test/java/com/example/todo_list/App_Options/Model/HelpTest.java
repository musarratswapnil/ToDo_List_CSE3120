package com.example.todo_list.App_Options.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HelpTest {
    private Help help;

    @Before
    public void setUp() {
        // Initialize the Help object before each test
        help = new Help("What is a todo list?", "A todo list is a list of tasks you need to complete.");
    }

    @Test
    public void testGetQuestion() {
        // Test to ensure the getQuestion method returns the correct question
        assertEquals("What is a todo list?", help.getQuestion());
    }

    @Test
    public void testGetAnswer() {
        // Test to ensure the getAnswer method returns the correct answer
        assertEquals("A todo list is a list of tasks you need to complete.", help.getAnswer());
    }
}
        // Test to ensure
