package com.example.todo_list.Note;


import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseSingleton {
    private static FirebaseDatabase instance;

    private FirebaseDatabaseSingleton() {
    }

    public static synchronized FirebaseDatabase getInstance() {
        if (instance == null) {
            instance = FirebaseDatabase.getInstance();
        }
        return instance;
    }
}

