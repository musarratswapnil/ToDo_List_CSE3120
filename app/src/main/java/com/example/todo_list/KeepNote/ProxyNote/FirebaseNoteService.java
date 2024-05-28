package com.example.todo_list.KeepNote.ProxyNote;

import com.example.todo_list.KeepNote.Listdata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseNoteService implements NoteService {
    private final DatabaseReference notesRef;

    public FirebaseNoteService() {
        notesRef = FirebaseDatabase.getInstance().getReference("notes");
    }

    @Override
    public void addNote(Listdata note) {
        String noteId = notesRef.push().getKey();
        note.setId(noteId);
        notesRef.child(noteId).setValue(note);
    }

    @Override
    public void updateNote(Listdata note) {
        notesRef.child(note.getId()).setValue(note);
    }

    @Override
    public void deleteNote(String noteId) {
        notesRef.child(noteId).removeValue();
    }

    @Override
    public void getNote(String noteId, NoteCallback callback) {
        notesRef.child(noteId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Listdata note = dataSnapshot.getValue(Listdata.class);
                callback.onNoteReceived(note);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.toException());
            }
        });
    }

    @Override
    public void getAllNotes(NotesCallback callback) {
        notesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Listdata> notes = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Listdata note = snapshot.getValue(Listdata.class);
                    notes.add(note);
                }
                callback.onNotesReceived(notes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.toException());
            }
        });
    }
}
