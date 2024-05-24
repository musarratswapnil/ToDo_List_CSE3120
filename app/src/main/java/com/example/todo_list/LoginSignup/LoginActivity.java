
package com.example.todo_list.LoginSignup;


import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.DashBoard_Option.DashboardActivity;
import com.example.todo_list.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;

    private Button loginWithGoogle;
    private EditText editTextPassword;
    private TextView forgotPasswordTextView;
    private FirebaseAuth mAuth;

    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    private PasswordResetHelper passwordResetHelper;
    public boolean isNetworkAvailable = true;
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
        loginWithGoogle = findViewById(R.id.button3);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("393767222391-0t9mjomvm2ddc6e71rifcvbk937cer9m.apps.googleusercontent.com") // replace with your actual server client ID
                .build();
        gsc = GoogleSignIn.getClient(this, gso);


        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        /*

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
*/
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


    public void signIn() {
        // Sign out from the current account
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // After signing out, revoke all tokens
                gsc.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // After revoking access, start the sign in process
                        Intent signInIntent = gsc.getSignInIntent();
                        startActivityForResult(signInIntent, 1000);
                    }
                });
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);  // Authenticate with Firebase using the Google account
            } catch (ApiException e) {
                Log.e(TAG, "Google sign in failed", e);
                Toast.makeText(getApplicationContext(), "Google sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateFirebaseWithGoogleAccount(acct);  // Call the method to update Firebase Database
                            navigateToSecondActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateFirebaseWithGoogleAccount(GoogleSignInAccount account) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser(); // Get the authenticated Firebase user
        if (firebaseUser != null) {
            String googleId = account.getId(); // Google ID
            String email = account.getEmail(); // Google email
            String name = account.getDisplayName(); // User's name

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

            Map<String, Object> userUpdates = new HashMap<>();
            userUpdates.put("googleId", googleId);
            userUpdates.put("email", email);
            userUpdates.put("name", name);

            databaseReference.child(firebaseUser.getUid()).updateChildren(userUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Firebase updated with Google info", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Failed to update Firebase", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    public void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    /*
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

    }*/
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
        if (checkEmailEmpty(email) && checkPasswordEmpty(password)) {
            editTextEmail.setError("Please fill in information to continue");
            Toast.makeText(LoginActivity.this, "Please fill in information to continue", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkEmailEmpty(email)) {
            editTextEmail.setError("Email is required");
            Toast.makeText(LoginActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkPasswordEmpty(password)){
            editTextPassword.setError("Password is required");
            Toast.makeText(LoginActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkPasswordLength(password)) {
            editTextPassword.setError("Password must be at least 6 characters");
            Toast.makeText(LoginActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
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

    public static boolean checkEmailEmpty(String email) {

        if(email.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean checkPasswordEmpty(String password){
        if(password.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean checkPasswordLength(String password){
        if(password.length()<6){
            return true;
        }
        else{
            return false;
        }
    }



}