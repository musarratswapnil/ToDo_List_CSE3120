package com.example.todo_list.DashBoard_Option;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todo_list.Reminder.HomeFragment;
import com.example.todo_list.Note.HomeScreen;
import com.example.todo_list.R;
import com.google.android.material.card.MaterialCardView;

public class OptionFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_option, container, false);

        // Find the card views
        MaterialCardView remainderCard = view.findViewById(R.id.RemainderCard);
        MaterialCardView noteCard = view.findViewById(R.id.NoteCard);

        // Set click listener for the RemainderCard
        remainderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the HomeFragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
            }
        });

        // Set click listener for the NoteCard
        noteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the HomeScreen activity
                Intent intent = new Intent(getActivity(), HomeScreen.class);
                startActivity(intent);
            }
        });

        return view;
    }
    }
