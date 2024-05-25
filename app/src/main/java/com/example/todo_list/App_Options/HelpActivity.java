package com.example.todo_list.App_Options;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.App_Options.Model.Help;
import com.example.todo_list.App_Options.Model.HelpAdapter;
import com.example.todo_list.R;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HelpAdapter helpAdapter;
    private List<Help> helpList = new ArrayList<>();
    private List<Help> filteredList = new ArrayList<>();
    private EditText searchInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        recyclerView = findViewById(R.id.recyclerviewItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchInput = findViewById(R.id.searchInput);

        helpAdapter = new HelpAdapter(filteredList);
        recyclerView.setAdapter(helpAdapter);

        FirebaseDatabaseHelper.getInstance().fetchHelpItems(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Help> helpItems) {
                helpList.clear();
                helpList.addAll(helpItems);
                filteredList.clear();
                filteredList.addAll(helpItems);
                helpAdapter.notifyDataSetChanged();
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filter(String text) {
        filteredList.clear();
        for (Help item : helpList) {
            if ((item.getQuestion() != null && item.getQuestion().toLowerCase().contains(text.toLowerCase())) ||
                    (item.getAnswer() != null && item.getAnswer().toLowerCase().contains(text.toLowerCase()))) {
                filteredList.add(item);
            }
        }
        helpAdapter.notifyDataSetChanged();
    }
}
