package com.example.todo_list.KeepNote;

import com.example.todo_list.KeepNote.FlyWeightColor.ColorFlyweight;
import com.example.todo_list.KeepNote.FlyWeightColor.ColorFlyweightFactory;

/**
 * Represents a single item in the todo list.
 */
public class Listdata {

    /** The unique identifier of the list item. */
    public String id;

    /** The title of the list item. */
    public String title;

    /** The description of the list item. */
    public String desc;

    /** The color of the list item. */
    private ColorFlyweight color;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(User.class).
     */
    public Listdata() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    /**
     * Constructs a Listdata object with specified parameters.
     *
     * @param id The unique identifier of the list item.
     * @param title The title of the list item.
     * @param desc The description of the list item.
     * @param color The color of the list item.
     */
    public Listdata(String id, String title, String desc, int color) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.color = ColorFlyweightFactory.getColor(color);
    }

    /**
     * Retrieves the title of the list item.
     *
     * @return The title of the list item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the list item.
     *
     * @param title The new title for the list item.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description of the list item.
     *
     * @return The description of the list item.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the description of the list item.
     *
     * @param desc The new description for the list item.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Retrieves the unique identifier of the list item.
     *
     * @return The unique identifier of the list item.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the list item.
     *
     * @param id The new unique identifier for the list item.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the color of the list item.
     *
     * @return The color of the list item.
     */
    public int getColor() {
        return color.getColor();
    }

    /**
     * Sets the color of the list item.
     *
     * @param color The new color for the list item.
     */
    public void setColor(int color) {
        this.color = ColorFlyweightFactory.getColor(color);
    }
}
