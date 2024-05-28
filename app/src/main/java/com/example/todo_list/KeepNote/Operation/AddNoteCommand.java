package com.example.todo_list.KeepNote.Operation;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todo_list.KeepNote.HomeScreen;
import com.example.todo_list.KeepNote.Listdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class AddNoteCommand implements Command {
    private DatabaseReference mDatabase;
    private Context context;
    private String title;
    private String description;
    private String userID;
    private int color;

    public AddNoteCommand(Context context, DatabaseReference databaseReference,String titlesend, String descsend,String userID,int color) {
        this.context = context;
        this.mDatabase = databaseReference;
        this.title=titlesend;
        this.description=descsend;
        this.userID=userID;
        this.color = color;
    }
   @Override
    public void executeNote() {
       if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
           Toast.makeText(context,"Field can't be empty",Toast.LENGTH_SHORT).show();

           return;
       }
        String itemId = mDatabase.push().getKey();
        Listdata listdata = new Listdata(itemId, title, description,color);
        mDatabase.child("users")
                .child(userID).child("notes").child(itemId).setValue(listdata)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"Note Added",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, HomeScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);                    }
                });
    }

}
