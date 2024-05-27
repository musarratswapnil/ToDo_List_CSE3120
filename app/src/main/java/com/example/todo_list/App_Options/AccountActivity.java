package com.example.todo_list.App_Options;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.todo_list.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This activity displays the user's account information including their name and email.
 * It also provides an option to change the password.
 */


public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userNameTextView;
    private TextView userEmailTextView;
    private TextView emailTextView;
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the most recent data supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        userNameTextView = findViewById(R.id.userName);
        userEmailTextView = findViewById(R.id.userEmail);
        emailTextView = findViewById(R.id.textView27);

        // Retrieve user data from Firebase and update TextViews
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            userEmailTextView.setText(userEmail);
            emailTextView.setText(userEmail);

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
            userRef.addValueEventListener(new ValueEventListener() {
                /**
                 * This method is called once with the initial value and again whenever data at this location is updated.
                 * @param dataSnapshot The data at this location.
                 */
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.child("name").getValue(String.class);
                        userNameTextView.setText(userName);
                    }
                }

                /**
                 * This method will be triggered in the event that this listener either failed at the server,
                 * or is removed as a result of the security and Firebase Database rules.
                 * @param databaseError A description of the error that occurred.
                 */
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }

        // Find password layout and components
        ImageView passwordArrow = findViewById(R.id.imageChangePasswordArrow);
        LinearLayout passwordLayout = findViewById(R.id.passwordChange);

        passwordArrow.setOnClickListener(this);
        passwordLayout.setOnClickListener(this);
    }
    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */

    @Override
    public void onClick(View v) {
        // Display the change password dialog
        ChangePasswordDialogFragment dialogFragment = new ChangePasswordDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "change_password_dialog");
    }
}
