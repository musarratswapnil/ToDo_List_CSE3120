package com.example.todo_list.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todo_list.Note.Operation.CommandInvoker;
import com.example.todo_list.Note.Operation.AddNoteCommand;
import com.example.todo_list.Note.Operation.Command;
import com.example.todo_list.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoteMainActivity extends AppCompatActivity {

   private EditText title,desc;
   private String titlesend,descsend;
   private CommandInvoker invoker ;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);
        initializeViews();
    }


    public void AddNotes(View view) {
        String titlesend = title.getText().toString();
        String descsend = desc.getText().toString();

        invoker.setCommand(new AddNoteCommand(getApplicationContext(), mDatabase, titlesend, descsend));
        invoker.executeCommand();
    }

    private void initializeViews() {

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        mDatabase = FirebaseDatabaseSingleton.getInstance().getReference().child("users").child("1");
        invoker = new CommandInvoker();

    }

}