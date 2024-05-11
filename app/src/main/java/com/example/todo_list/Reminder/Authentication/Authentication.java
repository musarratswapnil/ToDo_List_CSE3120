package com.example.todo_list.Reminder.Authentication;

import android.content.Intent;
import android.content.Context;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {
    Authentication(Context context){

            // Assuming Firebase Authentication setup
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(context.getApplicationContext(), "Not logged in!", Toast.LENGTH_SHORT).show();
//                context.startActivity(new Intent(context.getApplicationContext(), LoginActivity.class));
            }

    }

}
