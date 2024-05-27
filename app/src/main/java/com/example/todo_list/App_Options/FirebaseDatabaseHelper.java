/**
 * The FirebaseDatabaseHelper class provides methods to interact with Firebase Realtime Database for managing help items.
 */
package com.example.todo_list.App_Options;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.todo_list.App_Options.Model.Help;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private static FirebaseDatabaseHelper instance;
    private DatabaseReference databaseReference;

    /**
     * Private constructor to initialize the FirebaseDatabaseHelper with a DatabaseReference pointing to the root of the database.
     */
    private FirebaseDatabaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Package-private constructor used for testing purposes to inject a mock DatabaseReference.
     *
     * @param databaseReference The DatabaseReference instance to use.
     */
    FirebaseDatabaseHelper(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    /**
     * Gets the singleton instance of FirebaseDatabaseHelper.
     *
     * @return The singleton instance of FirebaseDatabaseHelper.
     */
    public static synchronized FirebaseDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseDatabaseHelper();
        }
        return instance;
    }

    /**
     * Gets the DatabaseReference for the "help" node.
     *
     * @return The DatabaseReference for the "help" node.
     */
    public DatabaseReference getHelpReference() {
        return databaseReference.child("help");
    }

    /**
     * Fetches help items from the Firebase Realtime Database and notifies the caller via DataStatus interface.
     *
     * @param dataStatus The callback interface to notify when data is loaded or when an error occurs.
     */
    public void fetchHelpItems(final DataStatus dataStatus) {
        getHelpReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Help> helpItems = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Help helpItem = postSnapshot.getValue(Help.class);
                    helpItems.add(helpItem);
                }
                dataStatus.DataIsLoaded(helpItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataStatus.DataIsLoaded(new ArrayList<>());
            }
        });
    }

    /**
     * The DataStatus interface to be implemented by classes that need to be notified when data is loaded from the database.
     */
    public interface DataStatus {
        /**
         * Called when the help items data is loaded from the database.
         *
         * @param helpItems The list of help items loaded from the database.
         */
        void DataIsLoaded(List<Help> helpItems);
    }
}
