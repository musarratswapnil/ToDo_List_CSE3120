package com.example.todo_list.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseAuthSingleton {
    private static volatile FirebaseAuthSingleton instance=null;
    private FirebaseAuth firebaseAuth;

    private FirebaseAuthSingleton() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseAuthSingleton getInstance() {
        if (instance == null) {
            synchronized (FirebaseAuthSingleton.class){
                if(instance == null){
                    instance = new FirebaseAuthSingleton();
                }
            }
        }
        return instance;
    }


}
