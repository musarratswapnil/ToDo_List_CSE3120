package com.example.todo_list.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo_list.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * SignupActivity handles user registration by taking user details such as name, email, and password,
 * and creating a new user account in Firebase Authentication. It also manages network connectivity changes.
 */
public class SignupActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextPassword2;
    private ImageView imageViewSignUp;
    private TextView loginTextView;
    private FirebaseAuth firebaseAuth;
    private boolean isNetworkAvailable = true;
    NetworkChangeReceiver networkChangeReceiver;
    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently supplied.
     *                           Otherwise it is null.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseService.getInstance().getFirebaseAuth();

        // Find views by ID
        editTextName = findViewById(R.id.editTextTextName);
        editTextEmail = findViewById(R.id.editTextTextEmail);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextPassword2 = findViewById(R.id.editTextTextPassword2);
        imageViewSignUp = findViewById(R.id.imageView5);
        loginTextView = findViewById(R.id.textView5);

        imageViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        // Register network change receiver
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    /**
     * Called when the activity is destroyed. Unregisters the network change receiver.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister network change receiver
        unregisterReceiver(networkChangeReceiver);
    }
    /**
     * Handles the sign-up process by validating user inputs and creating a new user in Firebase Authentication.
     */
    private void signUp() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();

        if (!isNetworkAvailable) {
            // No network connection, disable input fields
            editTextName.setEnabled(false);
            editTextEmail.setEnabled(false);
            editTextPassword.setEnabled(false);
            editTextPassword2.setEnabled(false);
            Toast.makeText(SignupActivity.this, "No internet connection. Please check your network settings.", Toast.LENGTH_LONG).show();
            return;
        }

        // Validate the input fields
        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            Toast.makeText(SignupActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            Toast.makeText(SignupActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            Toast.makeText(SignupActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password2.isEmpty()) {
            editTextPassword2.setError("Please confirm your password");
            Toast.makeText(SignupActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            Toast.makeText(SignupActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password2)) {
            editTextPassword2.setError("Passwords do not match");
            Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isValid(password)){
            editTextPassword.setError("Password is not valid.Need alphabets,special characters");
            Toast.makeText(SignupActivity.this, "Password is not valid.Need alphabets,special characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new user in Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Get the user ID of the newly created user
                            String userId = firebaseAuth.getCurrentUser().getUid();

                            // Save the name to Firebase Realtime Database under the user's ID
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                            usersRef.child(userId).child("name").setValue(name);

                            // Sign-up process
                            Toast.makeText(SignupActivity.this, "Sign-up successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        } else {
                            // Sign-up failed
                            String errorMessage = "Sign-up failed: " + task.getException().getMessage();
                            Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            editTextEmail.setError(errorMessage);
                        }
                    }
                });
    }
    /**
     * BroadcastReceiver to monitor network connectivity changes.
     */
    protected class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isNetworkAvailable()) {
                isNetworkAvailable = true;
                editTextName.setEnabled(true);
                editTextEmail.setEnabled(true);
                editTextPassword.setEnabled(true);
                editTextPassword2.setEnabled(true);
                Toast.makeText(SignupActivity.this, "Network connection available", Toast.LENGTH_SHORT).show();
            } else {
                isNetworkAvailable = false;
                editTextName.setEnabled(false);
                editTextEmail.setEnabled(false);
                editTextPassword.setEnabled(false);
                editTextPassword2.setEnabled(false);
                Toast.makeText(SignupActivity.this, "No internet connection. Please check your network settings.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * Checks if network is available.
     *
     * @return true if network is available, false otherwise.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    /**
     * Checks if the password is valid. A valid password must contain at least one letter, one digit, and one special character.
     *
     * @param password The password to check.
     * @return true if the password is valid, false otherwise.
     */
    public static boolean isValid(String password) {
        int f1 = 0, f2 = 0, f3 = 0;
        if(password.length()<6){
            return false;
        }
        else {

            for (int i = 0; i < password.length(); i++) {
                if (Character.isLetter(password.charAt(i))) {
                    f1 = 1;
                }
            }
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    f2 = 1;
                }
            }
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            if (f1 == 1 && f2 == 1 && f3 == 1) {
                return true;
            }
            return false;

        }

    }
}
