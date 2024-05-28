package com.example.todo_list.KeepNote.ProxyNote;

public interface Note {
    String getId();
    String getTitle();
    String getDesc();
    void setDesc(String desc);
}