package com.example.todo_list.LoginSignup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * FirebaseService is a singleton class that provides access to Firebase authentication
 * and database references.
 * It initializes FirebaseAuth and DatabaseReference objects, and ensures only one instance
 * of this service exists throughout the application lifecycle.
 */
public class FirebaseService {
    private static FirebaseService instance;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    /**
     * Private constructor to initialize FirebaseAuth and DatabaseReference objects.
     * This prevents direct instantiation of the class from outside.
     */
    private FirebaseService() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

    }
    /**
     * Returns the singleton instance of FirebaseService. If the instance is null,
     * it creates a new one.
     *
     * @return the singleton instance of FirebaseService.
     */
    public static synchronized FirebaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }
    /**
     * Returns the FirebaseAuth instance for authentication purposes.
     *
     * @return the FirebaseAuth instance.
     */

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
    /**
     * Returns the DatabaseReference instance for database operations.
     *
     * @return the DatabaseReference instance.
     */
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public  FirebaseUser getCurrentUser(){
        return currentUser;
    }
}
