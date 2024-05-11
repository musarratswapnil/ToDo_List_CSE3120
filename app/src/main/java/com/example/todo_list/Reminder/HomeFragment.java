package com.example.todo_list.Reminder;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.todo_list.DashBoard_Option.DashboardActivity;
import com.example.todo_list.Note.FirebaseDatabaseSingleton;
import com.example.todo_list.Note.NotesAdapter;
import com.example.todo_list.Reminder.Sort.SortByDateStrategy;
import com.example.todo_list.Reminder.Sort.SortByNameStrategy;
import com.example.todo_list.Reminder.Sort.SortingStrategy;
import com.example.todo_list.R;
import com.example.todo_list.Reminder.AddTaskFragment;
import com.example.todo_list.Reminder.Task;
import com.example.todo_list.Reminder.TaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FloatingActionButton addTaskButton;
    private DatabaseReference tasksRef;
    private ValueEventListener tasksValueEventListener;
    private List<Task> taskList;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter,taskAdapter2;
    private Spinner sortSpinner;

    private SortingStrategy sortingStrategy;


    FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mAuth = FirebaseAuth.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        String userId = firebaseAuth.getCurrentUser().getUid();

        // Create a reference to the tasks node in the Firebase database
        tasksRef = FirebaseDatabaseSingleton.getInstance().getReference("users")
                .child("1") // Replace with your user ID or appropriate path
                .child("tasks");

    }

    private void initializeFirebase() {
        tasksRef = FirebaseDatabase.getInstance().getReference("users").child("1").child("tasks");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        addTaskButton = view.findViewById(R.id.addTaskButton);
//        addTaskButton.setOnClickListener(this);
//
//        // Initialize the RecyclerView and its adapter
//        recyclerView = view.findViewById(R.id.recyclerView);
//        taskList = new ArrayList<>();
//        // Pass the tasksRef to the TaskAdapter
//        taskAdapter = new TaskAdapter(taskList, tasksRef, requireContext(), getParentFragmentManager()); // Use requireContext() instead of getContext()
//
//        // Set the adapter for the RecyclerView
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext())); // Use requireContext() instead of getActivity()
//        recyclerView.setAdapter(taskAdapter);
        initializeViews(view);
        setupSortingOptions();
        setupRecyclerView(view);
        return view;
    }

    private void initializeViews(View view) {
        sortSpinner=view.findViewById(R.id.sortSpinner);
//        sortingStrategy = new SortByDateStrategy();
        addTaskButton = view.findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(this);
    }

    private void setupRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, tasksRef, requireContext(), getParentFragmentManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(taskAdapter);
    }



    @Override
    public void onStart() {
        super.onStart();
        startListeningForTaskChanges();

//        // Initialize the ValueEventListener to listen for changes in the tasks node
//        tasksValueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                taskList.clear();
//                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
//                    Task task = taskSnapshot.getValue(Task.class);
//                    task.setKey(taskSnapshot.getKey()); // Set the key for each task
//                    taskList.add(task);
//                }
//                taskAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle error
//            }
//        };
//
//        // Start listening for changes in the tasks node
//        tasksRef.addValueEventListener(tasksValueEventListener);
    }

    private void startListeningForTaskChanges() {
        tasksValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    task.setKey(taskSnapshot.getKey());
                    taskList.add(task);
                }
                sortList();
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        };
        tasksRef.addValueEventListener(tasksValueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Stop listening for changes in the tasks node
        stopListeningForTaskChanges();

//        if (tasksValueEventListener != null) {
//            tasksRef.removeEventListener(tasksValueEventListener);
//        }
    }

    private void stopListeningForTaskChanges() {
        if (tasksValueEventListener != null) {
            tasksRef.removeEventListener(tasksValueEventListener);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addTaskButton) {
            // Open the AddTaskFragment
            openAddTaskFragment();

//            AddTaskFragment addTaskFragment = new AddTaskFragment();
//            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, addTaskFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
        }
    }

    private void openAddTaskFragment() {
        AddTaskFragment addTaskFragment = new AddTaskFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addTaskFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void updateRecyclerView(List<Task> updatedTaskList){
//        taskList.clear();
//        taskList.addAll(updatedTaskList);
        taskAdapter = new TaskAdapter(updatedTaskList, tasksRef, requireContext(), getParentFragmentManager());
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();


    }

    private void sortList() {
        if (sortingStrategy != null) {
            sortingStrategy.sort(taskList);
          updateRecyclerView(taskList);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //                Different Type of Sorting Type
    //                Strategy Pattern Body
    ///////////////////////////////////////////////////////////////////////////////////

    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
        sortList();
    }

    private void setupSortingOptions() {
        String[] sortingOptions = {"Sort by Date", "Sort by Name"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sortingOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 1:
                        setSortingStrategy(sortingStrategy = new SortByNameStrategy());
                        break;
                    case 0:
                        setSortingStrategy(sortingStrategy = new SortByDateStrategy());
                        break;
                    default:
                        setSortingStrategy(sortingStrategy = new SortByDateStrategy());
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(, "Selected Invalid option", Toast.LENGTH_SHORT).show();

            }
        });
    }

}