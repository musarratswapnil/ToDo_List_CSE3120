package com.example.todo_list.KeepNote;

import com.example.todo_list.KeepNote.FlyWeightColor.ColorFlyweight;
import com.example.todo_list.KeepNote.FlyWeightColor.ColorFlyweightFactory;

//
public class Listdata {

    public String id;
    public String title;
    public String desc;
    private ColorFlyweight color;

    public Listdata() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Listdata(String id,String title, String desc,int color) {
        this.id=id;
        this.title = title;
        this.desc = desc;
        this.color = ColorFlyweightFactory.getColor(color);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getColor() {
        return color.getColor();
    }

    public void setColor(int color) {
        this.color = ColorFlyweightFactory.getColor(color);
    }
}
//public class Listdata {
//    private final Note note;
//
//    public Listdata(String id, String title, String desc) {
//        this.note = new NoteProxy(id, title, desc);
//    }
//
//    public String getTitle() {
//        return note.getTitle();
//    }
//
//    public String getDesc() {
//        return note.getDesc();
//    }
//
//    public void setDesc(String desc) {
//        note.setDesc(desc);
//    }
//
//    public String getId() {
//        return note.getId();
//    }
//}
