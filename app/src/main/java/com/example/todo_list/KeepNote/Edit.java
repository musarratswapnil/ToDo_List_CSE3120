package com.example.todo_list.KeepNote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.KeepNote.ProxyNote.NoteServiceProxy;
import com.example.todo_list.LoginSignup.LoginActivity;
import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Edit extends AppCompatActivity {
    private EditText title, desc;
    private String titlesend, descsend, id, user;
    private NoteServiceProxy noteServiceProxy;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initializeViews();
        noteServiceProxy = new NoteServiceProxy();

        Intent i = getIntent();
        String gettitle = i.getStringExtra("title");
        String getdesc = i.getStringExtra("desc");
        id = i.getStringExtra("id");
        title.setText(gettitle);
        desc.setText(getdesc);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getApplicationContext(), "Not logged in!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }
        user = currentUser.getUid();
    }

    private void initializeViews() {
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
    }

    public void UpdateNotes(View view) {
        titlesend = title.getText().toString();
        descsend = desc.getText().toString();
        Listdata note = new Listdata(id, titlesend, descsend);
        noteServiceProxy.updateNote(note);
    }

    public void DeleteNotes(View view) {
        noteServiceProxy.deleteNote(id);
    }
}
