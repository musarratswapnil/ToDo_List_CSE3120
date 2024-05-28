package com.example.todo_list.KeepNote.ProxyNote;

public class NoteProxy implements Note {
    private final String id;
    private final String title;
    private RealNote realNote;
    private String desc;

    public NoteProxy(String id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    private void ensureRealNoteLoaded() {
        if (realNote == null) {
            // Simulate loading the real note, e.g., from a database
            realNote = new RealNote(id, title, desc);
        }
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
        ensureRealNoteLoaded();
        return realNote.getDesc();
    }

    @Override
    public void setDesc(String desc) {
        ensureRealNoteLoaded();
        realNote.setDesc(desc);
    }
}

