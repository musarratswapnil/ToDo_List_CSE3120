package com.example.todo_list.Note.Operation;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todo_list.Note.HomeScreen;
import com.example.todo_list.Note.Listdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class AddNoteCommand implements Command {
    private DatabaseReference mDatabase;
    private Context context;
    private String title;
    private String description;
    private String userID;
    public AddNoteCommand(Context context, DatabaseReference databaseReference,String titlesend, String descsend,String userID) {
        this.context = context;
        this.mDatabase = databaseReference;
        this.title=titlesend;
        this.description=descsend;
        this.userID=userID;
    }
   @Override
    public void executeNote() {
       if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
           Toast.makeText(context,"Field can't be empty",Toast.LENGTH_SHORT).show();

           return;
       }
        String itemId = mDatabase.push().getKey();
        Listdata listdata = new Listdata(itemId, title, description);
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
