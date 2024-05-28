package com.example.todo_list.KeepNote.Operation;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todo_list.KeepNote.HomeScreen;
import com.example.todo_list.KeepNote.Listdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;


public class UpdateNoteCommand implements Command {
        private DatabaseReference databaseReference;
        private String id;
        private String title;
        private String description;
        private Context context;
        private String userID;


        public UpdateNoteCommand(Context context,DatabaseReference databaseReference, String id, String title, String description,String userID) {
            this.databaseReference = databaseReference;
            this.id = id;
            this.title = title;
            this.description = description;
            this.context=context;
            this.userID=userID;
        }

        @Override
        public void executeNote() {
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                Toast.makeText(context,"Field can't be empty",Toast.LENGTH_SHORT).show();

                return;
            }
            Log.e("userID",userID);

            Listdata listdata = new Listdata(id, title, description);
            databaseReference.child("users").child(userID).child("notes").child(id).setValue(listdata)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context,"Note Updated",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, HomeScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
        }
    }


