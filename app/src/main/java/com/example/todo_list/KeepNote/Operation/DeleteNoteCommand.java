package com.example.todo_list.KeepNote.Operation;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.todo_list.KeepNote.HomeScreen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

public class DeleteNoteCommand implements Command {
    private DatabaseReference databaseReference;
    private String id;
    private Context context;
    private String userID;


    public DeleteNoteCommand(Context context, DatabaseReference databaseReference, String id,String userID) {
        this.databaseReference = databaseReference;
        this.id = id;
        this.context = context;
        this.userID=userID;

    }

    @Override
    public void executeNote() {
        databaseReference.child("users").child(userID).child("notes").child(id).removeValue()
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
