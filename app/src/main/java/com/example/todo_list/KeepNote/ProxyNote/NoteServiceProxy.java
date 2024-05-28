package com.example.todo_list.KeepNote.ProxyNote;

import com.example.todo_list.KeepNote.Listdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteServiceProxy implements NoteService {
    private final FirebaseNoteService firebaseNoteService;
    private final Map<String, Listdata> cache;
    private final List<Listdata> allNotesCache;

    public NoteServiceProxy() {
        this.firebaseNoteService = new FirebaseNoteService();
        this.cache = new HashMap<>();
        this.allNotesCache = new ArrayList<>();
    }

    @Override
    public void addNote(Listdata note) {
        firebaseNoteService.addNote(note);
        cache.put(note.getId(), note);
    }

    @Override
    public void updateNote(Listdata note) {
        firebaseNoteService.updateNote(note);
        cache.put(note.getId(), note);
    }

    @Override
    public void deleteNote(String noteId) {
        firebaseNoteService.deleteNote(noteId);
        cache.remove(noteId);
    }

    @Override
    public void getNote(String noteId, NoteCallback callback) {
        if (cache.containsKey(noteId)) {
            callback.onNoteReceived(cache.get(noteId));
        } else {
            firebaseNoteService.getNote(noteId, new NoteCallback() {
                @Override
                public void onNoteReceived(Listdata note) {
                    cache.put(noteId, note);
                    callback.onNoteReceived(note);
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            });
        }
    }

    @Override
    public void getAllNotes(NotesCallback callback) {
        if (!allNotesCache.isEmpty()) {
            callback.onNotesReceived(new ArrayList<>(allNotesCache));
        } else {
            firebaseNoteService.getAllNotes(new NotesCallback() {
                @Override
                public void onNotesReceived(List<Listdata> notes) {
                    allNotesCache.clear();
                    allNotesCache.addAll(notes);
                    callback.onNotesReceived(notes);
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            });
        }
    }
}

