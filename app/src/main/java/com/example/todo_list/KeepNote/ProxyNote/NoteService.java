package com.example.todo_list.KeepNote.ProxyNote;

import com.example.todo_list.KeepNote.Listdata;

import java.util.List;

public interface NoteService {
    void addNote(Listdata note);
    void updateNote(Listdata note);
    void deleteNote(String noteId);
    void getNote(String noteId, NoteCallback callback);
    void getAllNotes(NotesCallback callback);

    interface NoteCallback {
        void onNoteReceived(Listdata note);
        void onError(Exception e);
    }

    interface NotesCallback {
        void onNotesReceived(List<Listdata> notes);
        void onError(Exception e);
    }
}

