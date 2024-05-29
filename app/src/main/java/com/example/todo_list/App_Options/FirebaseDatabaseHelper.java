package com.example.todo_list.App_Options;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
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

    FirebaseDatabaseHelper(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
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

    public void addHelpItem(final Help helpItem, final DataStatus dataStatus) {
        getHelpReference().runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                long maxId = 0;
                for (MutableData child : mutableData.getChildren()) {
                    try {
                        long key = Long.parseLong(child.getKey());
                        if (key > maxId) {
                            maxId = key;
                        }
                    } catch (NumberFormatException e) {
                        // Handle cases where keys are not numerical
                    }
                }
                String nextId = String.valueOf(maxId + 1);
                mutableData.child(nextId).setValue(helpItem);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                if (committed) {
                    dataStatus.DataIsInserted();
                } else {
                    dataStatus.DataInsertFailed(databaseError.toException());
                }
            }
        });
    }


    public interface DataStatus {
        void DataIsLoaded(List<Help> helpItems);
        void DataIsInserted();
        void DataInsertFailed(Exception e);
    }
}
