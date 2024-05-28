package com.example.todo_list.KeepNote.FlyWeightColor;

import java.util.HashMap;
import java.util.Map;

public class ColorFlyweightFactory {
    private static final Map<Integer, ColorFlyweight> colors = new HashMap<>();

    public static ColorFlyweight getColor(int color) {
        if (!colors.containsKey(color)) {
            ConcreteColorFlyweight concreteColor = new ConcreteColorFlyweight();
            concreteColor.setColor(color);
            colors.put(color, concreteColor);
        }
        return colors.get(color);
    }
}
