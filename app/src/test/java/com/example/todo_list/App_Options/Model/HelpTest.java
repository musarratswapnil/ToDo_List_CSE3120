package com.example.todo_list.App_Options.Model;

import org.junit.Test;
import static org.junit.Assert.*;

public class HelpTest {

    @Test
    public void testNoArgConstructor() {
        Help help = new Help();
        assertNull(help.getQuestion());
        assertNull(help.getAnswer());
    }

    @Test
    public void testParameterizedConstructor() {
        Help help = new Help("What is Android?", "Android is a mobile operating system.");
        assertEquals("What is Android?", help.getQuestion());
        assertEquals("Android is a mobile operating system.", help.getAnswer());
    }

    @Test
    public void testGettersAndSetters() {
        Help help = new Help();
        help.setQuestion("What is Kotlin?");
        help.setAnswer("Kotlin is a programming language.");

        assertEquals("What is Kotlin?", help.getQuestion());
        assertEquals("Kotlin is a programming language.", help.getAnswer());
    }

    @Test
    public void testSetters() {
        Help help = new Help();
        help.setQuestion("New Question");
        help.setAnswer("New Answer");

        assertEquals("New Question", help.getQuestion());
        assertEquals("New Answer", help.getAnswer());
    }
}
