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
/**
 * MainActivity serves as the splash screen and initial entry point of the application.
 * It checks if a user is already authenticated with Firebase and directs them to the appropriate activity.
 */
public class MainActivity extends AppCompatActivity {
    private AppCompatButton loginButton;
    private AppCompatButton signUpButton;
    @Override
    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently supplied.
     *                           Otherwise it is null.
     */
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