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

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userNameTextView;
    private TextView userEmailTextView;
    private TextView emailTextView;

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
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.child("name").getValue(String.class);
                        userNameTextView.setText(userName);
                    }
                }

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

    @Override
    public void onClick(View v) {
        // Display the change password dialog
        ChangePasswordDialogFragment dialogFragment = new ChangePasswordDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "change_password_dialog");
    }
}
