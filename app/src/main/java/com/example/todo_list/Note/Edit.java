package com.example.todo_list.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todo_list.Note.Operation.AddNoteCommand;
import com.example.todo_list.Note.Operation.Command;
import com.example.todo_list.Note.Operation.CommandInvoker;
import com.example.todo_list.Note.Operation.DeleteNoteCommand;
import com.example.todo_list.Note.Operation.UpdateNoteCommand;
import com.example.todo_list.R;
import com.google.firebase.database.DatabaseReference;

public class Edit extends AppCompatActivity {
    EditText title,desc;
    String titlesend,descsend,id;
    private DatabaseReference mDatabase;
    private Listdata listdata;
    Button updates,delete;
    private CommandInvoker invoker ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initializeViews();
        Intent i=getIntent();
         String gettitle=i.getStringExtra("title");
         String getdesc=i.getStringExtra("desc");
         id=i.getStringExtra("id");
        title.setText(gettitle);
        desc.setText(getdesc);



    }

    private void initializeViews() {
        updates = findViewById(R.id.updatesbutton);
        delete = findViewById(R.id.deletedbutton);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        mDatabase = FirebaseDatabaseSingleton.getInstance().getReference().child("users").child("1");
        invoker= new CommandInvoker();
    }
    private void setupIntentData() {
        Intent intent = getIntent();
        String gettitle = intent.getStringExtra("title");
        String getdesc = intent.getStringExtra("desc");
        String id = intent.getStringExtra("id");
        title.setText(gettitle);
        desc.setText(getdesc);
    }

    public void UpdateNotes(View view){
        String titlesend = title.getText().toString();
        String descsend = desc.getText().toString();
        invoker.setCommand( new UpdateNoteCommand(getApplicationContext(),mDatabase, id, titlesend, descsend));
        invoker.executeCommand();
    }
    public void DeleteNotes(View view){
        invoker.setCommand( new DeleteNoteCommand(getApplicationContext(),mDatabase, id));
        invoker.executeCommand();
    }


//    private void setupButton(Button button, Command command) {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                command.executeNote();
//            }
//        });
//    }


}