package com.example.todo_list.CgpaCalculator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CgpaActivityTest {
    private Cgpa cgpa;


    @Before
    public void setUp() {
        cgpa = new Cgpa();

    }

    @Test
    public void testGetGradeValue() {
        assertEquals(4.0, cgpa.getGradeValue("A+"), 0.01);
        assertEquals(3.75, cgpa.getGradeValue("A"), 0.01);
        assertEquals(3.5, cgpa.getGradeValue("A-"), 0.01);
        assertEquals(3.25, cgpa.getGradeValue("B+"), 0.01);
        assertEquals(3.0, cgpa.getGradeValue("B"), 0.01);
        assertEquals(2.75, cgpa.getGradeValue("B-"), 0.01);
        assertEquals(2.5, cgpa.getGradeValue("C"), 0.01);
        assertEquals(2.25, cgpa.getGradeValue("D"), 0.01);
        assertEquals(0.0, cgpa.getGradeValue("F"), 0.01);
        assertEquals(0.0, cgpa.getGradeValue("E"), 0.01);
        assertEquals(0.0, cgpa.getGradeValue("Z"), 0.01);
    }


}