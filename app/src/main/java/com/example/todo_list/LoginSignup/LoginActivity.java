package com.example.todo_list.LoginSignup;


import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.DashBoard_Option.DashboardActivity;
import com.example.todo_list.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private SignInClient oneTapClient;

    private BeginSignInRequest signInRequest;
    private static final int REQ_ONE_TAP = 100;
    private Button loginWithGoogle,loginWithFacebook;
    private EditText editTextPassword;
    private TextView forgotPasswordTextView;
    private FirebaseAuth mAuth;
    private PasswordResetHelper passwordResetHelper;
    private boolean isNetworkAvailable = true;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseService.getInstance().getFirebaseAuth();
        passwordResetHelper = new PasswordResetHelper();

        editTextEmail = findViewById(R.id.editTextTextEmail);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        forgotPasswordTextView = findViewById(R.id.textView23);
        ImageView imageViewLogin = findViewById(R.id.imageView5);
        TextView registerTextView = findViewById(R.id.textView5);
        loginWithGoogle=findViewById(R.id.button3);
        loginWithFacebook=findViewById(R.id.button4);
        oneTapClient = Identity.getSignInClient(this);

        signInRequest = BeginSignInRequest.builder()

                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()

                        .setSupported(true)

                        .build())

                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()

                        .setSupported(true)

                        // Your server's client ID, not your Android client ID.

                        .setServerClientId("393767222391-fsb3p8nfkduc6ltm7h53p8u0va5rd1ta.apps.googleusercontent.com") // TODO

                        // Only show accounts previously used to sign in.

                        .setFilterByAuthorizedAccounts(false)

                        .build())

                // Automatically sign in when exactly one credential is retrieved.

                .setAutoSelectEnabled(true)

                .build();

        imageViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    editTextEmail.setError("Email is Required");
                    editTextEmail.requestFocus();
                } else {
                    passwordResetHelper.resetPassword(email, LoginActivity.this);
                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signUpIntent);
            }
        });



        // Register network change receiver
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    public void buttonGoogleSignIn(View view){
        oneTapClient.beginSignIn(signInRequest)

                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {

                    @Override

                    public void onSuccess(BeginSignInResult result) {

                        try {

                            startIntentSenderForResult(

                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,

                                    null, 0, 0, 0);

                        } catch (IntentSender.SendIntentException e) {

                            Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());

                        }

                    }

                })

                .addOnFailureListener(this, new OnFailureListener() {

                    @Override

                    public void onFailure(@NonNull Exception e) {

                        // No saved credentials found. Launch the One Tap sign-up flow, or

                        // do nothing and continue presenting the signed-out UI.

                        Log.d(TAG, e.getLocalizedMessage());

                    }

                });


    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQ_ONE_TAP:

                try {

                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);

                    String idToken = credential.getGoogleIdToken();

                    String username = credential.getId();

                    String password = credential.getPassword();

                    Toast.makeText(LoginActivity.this, "Authentication done. Username is "+username, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                    if (idToken !=  null) {

                        // Got an ID token from Google. Use it to authenticate

                        // with your backend.

                        Log.d(TAG, "Got ID token.");

                    } else if (password != null) {

                        // Got a saved username and password. Use them to authenticate

                        // with your backend.

                        Log.d(TAG, "Got password.");

                    }

                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }

                break;

        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister network change receiver
        unregisterReceiver(networkChangeReceiver);
    }

    private void login() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (!isNetworkAvailable) {
            editTextEmail.setEnabled(false);
            editTextPassword.setEnabled(false);
            Toast.makeText(LoginActivity.this, "No internet connection. Please check your network settings.", Toast.LENGTH_LONG).show();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed. Please check your credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isNetworkAvailable()) {
                isNetworkAvailable = true;
                editTextEmail.setEnabled(true);
                editTextPassword.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Network connection available", Toast.LENGTH_SHORT).show();
            } else {
                isNetworkAvailable = false;
                editTextEmail.setEnabled(false);
                editTextPassword.setEnabled(false);
                Toast.makeText(LoginActivity.this, "No internet connection. Please check your network settings.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}