package com.example.todo_list.Note.Operation;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.todo_list.Note.HomeScreen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

public class DeleteNoteCommand implements Command {
    private DatabaseReference databaseReference;
    private String id;
    private Context context;

    public DeleteNoteCommand(Context context, DatabaseReference databaseReference, String id) {
        this.databaseReference = databaseReference;
        this.id = id;
        this.context = context;
    }

    @Override
    public void executeNote() {
        databaseReference.child("notes").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, HomeScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
    }
}
