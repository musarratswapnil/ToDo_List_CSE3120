package com.example.todo_list.App_Options;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.App_Options.Model.Help;
import com.example.todo_list.App_Options.Model.HelpAdapter;
import com.example.todo_list.R;

import java.util.ArrayList;
import java.util.List;

public class HelpFragment extends Fragment {

    private RecyclerView recyclerView;
    private HelpAdapter helpAdapter;
    private List<Help> helpList = new ArrayList<>();
    private List<Help> filteredList = new ArrayList<>();
    private EditText searchInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchInput = view.findViewById(R.id.searchInput);

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

        return view;
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
