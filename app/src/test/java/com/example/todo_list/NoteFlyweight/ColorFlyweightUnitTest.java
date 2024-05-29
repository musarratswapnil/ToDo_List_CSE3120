package com.example.todo_list.NoteFlyweight;

import static org.junit.Assert.assertEquals;

import com.example.todo_list.KeepNote.FlyWeightColor.ConcreteColorFlyweight;

import org.junit.Test;
/**
 * Unit tests for the ConcreteColorFlyweight class.
 */
public class ColorFlyweightUnitTest {
    /**
     * Tests that the color can be set and retrieved correctly.
     */
    @Test
    public void testConcreteColorFlyweight_SetAndGetColor() {
        ConcreteColorFlyweight color = new ConcreteColorFlyweight();
        color.setColor(0x0000FF); // Blue

        assertEquals( 0x0000FF, color.getColor());
    }
    /**
     * Tests that setting the same color multiple times works correctly.
     */
    @Test
    public void testSetSameColorMultipleTimes() {
        ConcreteColorFlyweight color = new ConcreteColorFlyweight();
        color.setColor(0x0000FF); // Blue
        color.setColor(0x0000FF); // Blue again

        assertEquals(0x0000FF, color.getColor());
    }
    /**
     * Tests that the color is updated correctly when different colors are set.
     */
    @Test
    public void testSetDifferentColors() {
        ConcreteColorFlyweight color = new ConcreteColorFlyweight();
        color.setColor(0x0000FF); // Blue
        color.setColor(0x00FF00); // Green

        assertEquals("The color should be updated to green", 0x00FF00, color.getColor());
    }


}
