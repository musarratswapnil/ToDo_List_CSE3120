package com.example.todo_list.KeepNote.FlyWeightColor;

import java.util.HashMap;
import java.util.Map;
/**
 * Concrete implementation of the ColorFlyweight interface.
 */
public class ColorFlyweightFactory {
    //    private static final Map<Integer, ColorFlyweight> colors = new HashMap<>();
//
//    public static ColorFlyweight getColor(int color) {
//        if (!colors.containsKey(color)) {
//            ConcreteColorFlyweight concreteColor = new ConcreteColorFlyweight();
//            concreteColor.setColor(color);
//            colors.put(color, concreteColor);
//        }
//        return colors.get(color);
//    }
//}
    private static final Map<Integer, ColorFlyweight> colors = new HashMap<>();

    /**
     * Returns a ColorFlyweight instance for the specified color.
     *
     * @param color the color
     * @return the ColorFlyweight instance
     */
    public static ColorFlyweight getColor(int color) {
        if (!colors.containsKey(color)) {
            ConcreteColorFlyweight concreteColor = new ConcreteColorFlyweight();
            concreteColor.setColor(color);
            colors.put(color, concreteColor);
        }
        return colors.get(color);
    }

    /**
     * Returns the number of unique colors created.
     *
     * @return the number of unique colors
     */
    public static int getColorCount() {
        return colors.size();
    }

    /**
     * Clears all colors from the factory.
     */
    public static void clearColors() {
        colors.clear();
    }
}