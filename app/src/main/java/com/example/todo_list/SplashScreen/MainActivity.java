package com.example.todo_list.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todo_list.DashBoard_Option.DashboardActivity;
import com.example.todo_list.LoginSignup.LoginActivity;
import com.example.todo_list.LoginSignup.SignupActivity;
import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton loginButton;
    private AppCompatButton signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        loginButton = findViewById(R.id.button);
        signUpButton = findViewById(R.id.button2);

        if (currentUser != null) {
            // User is authenticated, go to the dashboard activity
            Intent dashboardIntent = new Intent (MainActivity.this, DashboardActivity.class);
            startActivity(dashboardIntent);
            finish();
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  handle login button click
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }


        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle sign up button click
                Intent signUpIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signUpIntent);
            }
        });

    }

}
