package com.example.todo_list.LoginSignup;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
/**
 * PasswordResetHelper is a helper class that handles the process of sending password reset emails
 * using Firebase Authentication. It provides methods to send password reset emails to both Activities
 * and Fragments.
 */
public class PasswordResetHelper {
    private FirebaseAuth mAuth;
    /**
     * Constructor initializes the FirebaseAuth instance.
     */
    public PasswordResetHelper() {
        mAuth = FirebaseAuth.getInstance();
    }
    /**
     * Sends a password reset email to the specified email address.
     * Displays a Toast message indicating success or failure.
     *
     * @param email    The email address to which the password reset email should be sent.
     * @param activity The Activity context from which this method is called.
     */
    public void resetPassword(String email, final Activity activity) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "Password reset email sent. Please check your email.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(activity, "Failed to send password reset email. Please check the email address.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    /**
     * Sends a password reset email to the specified email address.
     * Displays a Toast message indicating success or failure.
     *
     * @param email    The email address to which the password reset email should be sent.
     * @param fragment The Fragment context from which this method is called.
     */
    public void resetPassword(String email, final Fragment fragment) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(fragment.requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(fragment.requireContext(), "Password reset email sent. Please check your email.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(fragment.requireContext(), "Failed to send password reset email. Please check the email address.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
