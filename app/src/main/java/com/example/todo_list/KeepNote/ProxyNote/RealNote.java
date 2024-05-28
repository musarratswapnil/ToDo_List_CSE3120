package com.example.todo_list.KeepNote.ProxyNote;

public class RealNote implements Note {
    private final String id;
    private final String title;
    private String desc;

    public RealNote(String id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
