package com.example.todo_list.App_Options;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseHelper {
    private static FirebaseDatabaseHelper instance;
    private DatabaseReference databaseReference;

    private FirebaseDatabaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static synchronized FirebaseDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseDatabaseHelper();
        }
        return instance;
    }

    public DatabaseReference getHelpReference() {
        return databaseReference.child("help");
    }
}
