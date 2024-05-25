package com.example.todo_list.App_Options;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.todo_list.LoginSignup.PasswordResetHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This DialogFragment provides an interface for the user to change their password.
 * It uses Firebase Authentication to handle password reset requests.
 */
public class ChangePasswordDialogFragment extends DialogFragment {
    private PasswordResetHelper passwordResetHelper;
    /**
     * Called to do initial creation of a fragment.
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordResetHelper = new PasswordResetHelper();
    }
    /**
     * Called to create a dialog for the fragment.
     * @param savedInstanceState If the dialog is being re-created from a previous saved state, this is the state.
     * @return A new dialog instance to be displayed by the fragment.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Change Password")
                .setMessage("Do you want to change your password?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {/**
                     * Called when the "Yes" button is clicked.
                     * @param dialog The dialog that received the click.
                     * @param id The button that was clicked.
                     */
                        // Get the current user's email
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userEmail = user.getEmail();

                        if (userEmail != null) {
                            // Call the password reset method with the user's email
                            passwordResetHelper.resetPassword(userEmail, requireActivity());
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    /**
                     * Called when the "No" button is clicked.
                     * @param dialog The dialog that received the click.
                     * @param id The button that was clicked.
                     */
                    public void onClick(DialogInterface dialog, int id) {
                        // Close the dialog when the user selects "No"
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
