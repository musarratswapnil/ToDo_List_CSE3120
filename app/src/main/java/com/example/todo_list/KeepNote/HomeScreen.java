package com.example.todo_list.KeepNote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.KeepNote.ProxyNote.NoteService;
import com.example.todo_list.KeepNote.ProxyNote.NoteServiceProxy;
import com.example.todo_list.KeepNote.Sort.SortByNameStrategy;
import com.example.todo_list.KeepNote.Sort.SortingStrategy;
import com.example.todo_list.LoginSignup.LoginActivity;
import com.example.todo_list.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<Listdata> list = new ArrayList<>();
    private SortingStrategy sortingStrategy;
    private Spinner sortSpinner;
    private FloatingActionButton fab;
    private FirebaseUser currentUser;
    private String userId;
    private NoteServiceProxy noteServiceProxy;

    private static final int MENU_ADD_NOTE = 1;
    private static final int MENU_ADD_CHECKLIST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        initializeView();
        setupSortingOptions();
        setupToolbar();
        setupRecyclerView();
        setupFabButton();

        noteServiceProxy = new NoteServiceProxy();
        setupDatabaseListener();
    }

    private void initializeView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        sortingStrategy = new SortByNameStrategy();
        sortSpinner = findViewById(R.id.sortSpinner);
    }

    private void setupSortingOptions() {
        String[] sortingOptions = {"Sort by Name", "Sort by Date"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortingOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setSortingStrategy(new SortByNameStrategy());
                        break;
                    case 1:
                        // setSortingStrategy(new SortByDateStrategy()); // Uncomment and implement SortByDateStrategy
                        break;
                    default:
                        setSortingStrategy(new SortByNameStrategy());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(HomeScreen.this, "Selected Invalid option", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupFabButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    private void setupDatabaseListener() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getApplicationContext(), "Not logged in!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }
        userId = currentUser.getUid();

        noteServiceProxy.getAllNotes(new NoteService.NotesCallback() {
            @Override
            public void onNotesReceived(List<Listdata> notes) {
                list.clear();
                list.addAll(notes);
                sortList();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(HomeScreen.this, "Error loading notes: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView() {
        NotesAdapter notesAdapter = new NotesAdapter(list, this);
        recyclerView.setAdapter(notesAdapter);
        notesAdapter.notifyDataSetChanged();
    }

    private void sortList() {
        if (sortingStrategy != null) {
            sortingStrategy.sort(list);
            updateRecyclerView();
        }
    }

    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
        sortList();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.float_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.menu_add_note) {
                    startActivity(new Intent(getApplicationContext(), NoteMainActivity.class));
                    return true;
                } else if (itemId == R.id.menu_add_checklist) {
                    // startActivity(new Intent(getApplicationContext(), AddChecklistActivity.class));
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }
}