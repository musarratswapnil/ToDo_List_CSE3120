package com.example.todo_list.App_Options;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todo_list.App_Options.Model.Help;
import com.example.todo_list.App_Options.Model.HelpAdapter;
import com.example.todo_list.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HelpFragment extends Fragment {
    private DatabaseReference helpRef;
    private RecyclerView recyclerView;
    private HelpAdapter helpAdapter;
    private List<Help> helpList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        helpRef = FirebaseDatabaseHelper.getInstance().getHelpReference();

        helpRef.addValueEventListener(new ValueEventListener() {
            int i=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    i++;

                    String question = dataSnapshot.child(Integer.toString(i)).child("question").getValue(String.class);
                    String answer = dataSnapshot.child(Integer.toString(i)).child("answer").getValue(String.class);
                    Help help = new Help(question, answer);

                    // Assuming Help is a simple model class that holds question and answer
//                helpList.clear();
                    helpList.add(help);

                    // Initialize adapter if null, otherwise just notify data changed
                    if (helpAdapter == null) {
                        helpAdapter = new HelpAdapter(helpList);
                        recyclerView.setAdapter(helpAdapter);
                    } else {
                        helpAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Listener was cancelled");
            }
        });

        return view;
    }
}