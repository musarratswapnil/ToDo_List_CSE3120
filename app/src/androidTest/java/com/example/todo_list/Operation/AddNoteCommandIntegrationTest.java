package com.example.todo_list.Operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.todo_list.KeepNote.Operation.AddNoteCommand;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AddNoteCommandIntegrationTest {




    private Context context;

    private DatabaseReference databaseReference;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        FirebaseApp.initializeApp(context);
        databaseReference = FirebaseDatabase.getInstance().getReference("notes");
    }

    @Test
    public void executeNote_withValidData_shouldAddNoteToFirebase() {
        // Arrange
        String title = "Test Title";
        String description = "Test Description";
        CountDownLatch latch = new CountDownLatch(1);

        // Create AddNoteCommand instance
        AddNoteCommand addNoteCommand = new AddNoteCommand(context, databaseReference, title, description);

        // Act
        addNoteCommand.executeNote();

                // Assert
                databaseReference.orderByChild("title").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                                String fetchedTitle = noteSnapshot.child("title").getValue(String.class);
                                String fetchedDescription = noteSnapshot.child("description").getValue(String.class);
                                assertEquals(title, fetchedTitle);
                                assertEquals(description, fetchedDescription);
                                latch.countDown();
                            }
                        } else {
                            fail("Data not found in Firebase.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        fail("DatabaseError: " + databaseError.getMessage());
                        latch.countDown();
                    }
                });

        try {
            assertTrue("Timeout waiting for Firebase response", latch.await(10, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            fail("Test interrupted: " + e.getMessage());
        }
    }

    @Test
    public void executeNote_withEmptyFields_shouldShowToast() {
        // Arrange
        AddNoteCommand addNoteCommand = new AddNoteCommand(context, databaseReference, "", "");

        // Act
        addNoteCommand.executeNote();

        // Assert
        // Here we assume that the AddNoteCommand class shows a toast when fields are empty
        // This part is tricky to assert as it involves UI. You might need to refactor your code to allow better testability.
    }
    }

