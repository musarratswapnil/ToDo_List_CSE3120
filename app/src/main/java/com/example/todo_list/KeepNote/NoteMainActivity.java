package com.example.todo_list.KeepNote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.KeepNote.Operation.AddNoteCommand;
import com.example.todo_list.KeepNote.Operation.CommandInvoker;
import com.example.todo_list.LoginSignup.LoginActivity;
import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class NoteMainActivity extends AppCompatActivity {

    private EditText title,desc;
    private String titlesend,descsend;
    private CommandInvoker invoker ;

    private DatabaseReference mDatabase;
    private String userId;
    private RadioGroup colorGroup;
    private int selectedColor = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);
        initializeViews();
      // setupColorGroupListener();

    }


    public void AddNotes(View view) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // User is not authenticated, handle accordingly
            Toast.makeText(getApplicationContext(), "Not logged in!", Toast.LENGTH_SHORT).show();
            // Redirect to login page
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }
        userId = currentUser.getUid();
        String titlesend = title.getText().toString();
        String descsend = desc.getText().toString();
//        int checkedId = colorGroup.getCheckedRadioButtonId();



        invoker.setCommand(new AddNoteCommand(getApplicationContext(), mDatabase, titlesend, descsend,userId,selectedColor));
        invoker.executeCommand();
    }

    private void initializeViews() {

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        colorGroup=findViewById(R.id.colorGroup);

        mDatabase = FirebaseDatabaseSingleton.getInstance().getReference();
        invoker = new CommandInvoker();

    }

        public void onColorSelected(View view) {
            boolean checked = ((RadioButton) view).isChecked();
            if (view.getId() == R.id.colorskyblue) {
                selectedColor = 1;
            } else if (view.getId() == R.id.colorGreen) {
                selectedColor = 2;
            } else if (view.getId() == R.id.colorPink) {
                selectedColor = 3;
            }
        }

}












