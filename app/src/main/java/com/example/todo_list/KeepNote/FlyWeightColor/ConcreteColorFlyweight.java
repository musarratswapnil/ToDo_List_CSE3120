package com.example.todo_list.KeepNote.FlyWeightColor;

public class ConcreteColorFlyweight implements ColorFlyweight {
    private int color;

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
