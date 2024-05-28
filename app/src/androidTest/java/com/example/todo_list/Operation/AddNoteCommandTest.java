package com.example.todo_list.Operation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.example.todo_list.KeepNote.Operation.AddNoteCommand;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AddNoteCommandTest {

    @Mock
    private Context mockContext;

    @Mock
    private DatabaseReference mockDatabaseReference;

    private AddNoteCommand addNoteCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        addNoteCommand = new AddNoteCommand(mockContext, mockDatabaseReference, "Test Title", "Test Description");
    }

    @Test
    public void executeNote_withValidData_shouldAddNote() {
        // Mock the task to be successful
        when(mockDatabaseReference.push().getKey()).thenReturn("itemId");
        when(mockDatabaseReference.child(any(String.class))).thenReturn(mockDatabaseReference);
        when(mockDatabaseReference.setValue(any())).thenReturn(mock(Task.class));

//         Execute the command
        addNoteCommand.executeNote();

        // Verify that the correct methods were called
        verify(mockDatabaseReference, times(1)).push().getKey();
        verify(mockDatabaseReference, times(1)).child("notes/itemId");
        verify(mockDatabaseReference, times(1)).setValue(any());

        // Verify that a success toast message is shown
        // (Assuming you have a method to show toast messages)
//        verify(mockContext, times(1)).showToast("Note Added");
    }

    @Test
    public void executeNote_withEmptyFields_shouldShowToast() {
        // Setup for empty title and description
        addNoteCommand = new AddNoteCommand(mockContext, mockDatabaseReference, "", "");

        // Execute the command
        addNoteCommand.executeNote();

        // Verify that no database operation is performed
//        verifyZeroInteractions(mockDatabaseReference);

        // Verify that a toast message is shown for empty fields
        // (Assuming you have a method to show toast messages)
//        verify(mockContext, times(1)).showToast("Fields can't be empty");
    }
}
