package com.example.todo_list.KeepNote.FlyWeightColor;

/**
 * Flyweight interface for colors.
 */
public interface ColorFlyweight {
    /**
     * Sets the color.
     *
     * @param color the color to set
     */
    void setColor(int color);

    /**
     * Gets the color.
     *
     * @return the current color
     */
    int getColor();
}
