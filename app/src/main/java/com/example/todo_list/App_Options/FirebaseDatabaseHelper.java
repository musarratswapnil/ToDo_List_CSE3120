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

    public interface DataStatus {
        void DataIsLoaded(List<Help> helpItems);
    }
}
