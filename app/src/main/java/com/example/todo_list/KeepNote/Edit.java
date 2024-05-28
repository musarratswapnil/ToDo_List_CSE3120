package com.example.todo_list.KeepNote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.KeepNote.Operation.CommandInvoker;
import com.example.todo_list.KeepNote.Operation.DeleteNoteCommand;
import com.example.todo_list.KeepNote.Operation.UpdateNoteCommand;
import com.example.todo_list.LoginSignup.LoginActivity;
import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//
//public class Edit extends AppCompatActivity {
//    private EditText title, desc;
//    private String titlesend, descsend, id, user;
//    private NoteServiceProxy noteServiceProxy;
//    private FirebaseUser currentUser;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit);
//        initializeViews();
//        noteServiceProxy = new NoteServiceProxy();
//
//        Intent i = getIntent();
//        String gettitle = i.getStringExtra("title");
//        String getdesc = i.getStringExtra("desc");
//        id = i.getStringExtra("id");
//        title.setText(gettitle);
//        desc.setText(getdesc);
//
//        currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser == null) {
//            Toast.makeText(getApplicationContext(), "Not logged in!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            return;
//        }
//        user = currentUser.getUid();
//    }
//
//    private void initializeViews() {
//        title = findViewById(R.id.title);
//        desc = findViewById(R.id.desc);
//    }
//
//    public void UpdateNotes(View view) {
//        titlesend = title.getText().toString();
//        descsend = desc.getText().toString();
//        Listdata note = new Listdata(id, titlesend, descsend);
//        noteServiceProxy.updateNote(note);
//    }
//
//    public void DeleteNotes(View view) {
//        noteServiceProxy.deleteNote(id);
//    }
//}
public class Edit extends AppCompatActivity {
    EditText title,desc;
    String titlesend,descsend,id ,user;
    private DatabaseReference mDatabase;
    private Listdata listdata;
    Button updates,delete;
    private CommandInvoker invoker ;
    private FirebaseUser currentUser;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initializeViews();
        Intent i=getIntent();
        String gettitle=i.getStringExtra("title");
        String getdesc=i.getStringExtra("desc");
        int color = getIntent().getIntExtra("color",1);
        id=i.getStringExtra("id");
        title.setText(gettitle);
        desc.setText(getdesc);
        setupIntentData();


    }

    private void initializeViews() {
        updates = findViewById(R.id.updatesbutton);
        delete = findViewById(R.id.deletedbutton);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        invoker= new CommandInvoker();
    }
    private void setupIntentData() {
        Intent intent = getIntent();
        String gettitle = intent.getStringExtra("title");
        String getdesc = intent.getStringExtra("desc");
        String id = intent.getStringExtra("id");
        color = getIntent().getIntExtra("color",1);
        title.setText(gettitle);
        desc.setText(getdesc);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            // User is not authenticated, handle accordingly
            Toast.makeText(getApplicationContext(), "Not logged in!", Toast.LENGTH_SHORT).show();
            // Redirect to login page
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }
        user = currentUser.getUid();
        Log.d("EditActivity", "User ID: " + user); // Add log to check user ID

    }

    public void UpdateNotes(View view){
        String titlesend = title.getText().toString();
        String descsend = desc.getText().toString();
//        userId = currentUser.getUid();

        invoker.setCommand( new UpdateNoteCommand(getApplicationContext(),mDatabase, id, titlesend, descsend,user,color));
        invoker.executeCommand();
    }
    public void DeleteNotes(View view){
        invoker.setCommand( new DeleteNoteCommand(getApplicationContext(),mDatabase, id,user));
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