package com.example.todo_list.NoteFlyweight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import com.example.todo_list.KeepNote.FlyWeightColor.ColorFlyweight;
import com.example.todo_list.KeepNote.FlyWeightColor.ColorFlyweightFactory;

import org.junit.Before;
import org.junit.Test;

public class ColorFlyweightITest {
    @Before
    public void setUp() {
        // Clear the color map before each test to ensure isolation
        ColorFlyweightFactory.clearColors();
    }

    /**
     * Tests that the same color returns the same instance.
     */
    @Test
    public void testGetColor_SameColorReturnsSameInstance() {
        ColorFlyweight color1 = ColorFlyweightFactory.getColor(0xFF0000); // Red
        ColorFlyweight color2 = ColorFlyweightFactory.getColor(0xFF0000); // Red

        assertSame("Should return the same instance for the same color", color1, color2);
    }

    /**
     * Tests that different colors return different instances.
     */
    @Test
    public void testGetColor_DifferentColorsReturnDifferentInstances() {
        ColorFlyweight color1 = ColorFlyweightFactory.getColor(0xFF0000); // Red
        ColorFlyweight color2 = ColorFlyweightFactory.getColor(0x00FF00); // Green

        assertNotSame("Should return different instances for different colors", color1, color2);
    }

    /**
     * Tests that the factory handles a large number of unique colors efficiently.
     */
    @Test
    public void testFactoryWithLargeNumberOfColors() {
        for (int i = 0; i < 10000; i++) {
            ColorFlyweight color = ColorFlyweightFactory.getColor(i);
            assertEquals("The color should match the requested color", i, color.getColor());
        }

        assertEquals("Should have 10000 unique colors created", 10000, ColorFlyweightFactory.getColorCount());
    }

    /**
     * Tests that the factory correctly clears all colors.
     */
    @Test
    public void testFactoryReset() {
        ColorFlyweight color1 = ColorFlyweightFactory.getColor(0xFF0000); // Red
        ColorFlyweightFactory.clearColors();
        ColorFlyweight color2 = ColorFlyweightFactory.getColor(0xFF0000); // Red

        assertNotSame("After clearing, a new instance should be created", color1, color2);
    }

    /**
     * Tests that the factory handles negative color values correctly.
     */
    @Test
    public void testFactoryWithNegativeColors() {
        ColorFlyweight color1 = ColorFlyweightFactory.getColor(-1);
        assertEquals("The color should match the requested color", -1, color1.getColor());

        ColorFlyweight color2 = ColorFlyweightFactory.getColor(-1);
        assertSame("Should return the same instance for the same negative color", color1, color2);
    }

    /**
     * Tests that the factory handles boundary color values correctly.
     */
    @Test
    public void testFactoryWithBoundaryColorValues() {
        ColorFlyweight color1 = ColorFlyweightFactory.getColor(0);
        assertEquals("The color should match the requested color", 0, color1.getColor());

        ColorFlyweight color2 = ColorFlyweightFactory.getColor(Integer.MAX_VALUE);
        assertEquals("The color should match the requested color", Integer.MAX_VALUE, color2.getColor());

        assertNotSame("Should return different instances for different boundary colors", color1, color2);
    }

    /**
     * Tests that the factory handles concurrent access correctly.
     */
    @Test
    public void testConcurrentAccess() throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                ColorFlyweight color = ColorFlyweightFactory.getColor(i);
                assertEquals("The color should match the requested color", i, color.getColor());
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertEquals("Should have 1000 unique colors created", 1000, ColorFlyweightFactory.getColorCount());
    }
}
