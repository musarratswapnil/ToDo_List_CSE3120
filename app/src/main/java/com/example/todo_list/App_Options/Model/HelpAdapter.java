package com.example.todo_list.App_Options.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.App_Options.Model.Help;
import com.example.todo_list.R;

import java.util.ArrayList;
import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {
    private List<Help> helpItems = new ArrayList<>();

    // Constructor
    public HelpAdapter(List<Help> helpItems) {
        this.helpItems = helpItems;
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_item, parent, false);
        return new HelpViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {
        Help currentItem = helpItems.get(position);
        holder.questionTextView.setText(currentItem.getQuestion());
        holder.answerTextView.setText(currentItem.getAnswer());
    }

    @Override
    public int getItemCount() {
        return helpItems.size();
    }

    // ViewHolder class
    static class HelpViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView, answerTextView;

        HelpViewHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.question);
            answerTextView = itemView.findViewById(R.id.answer);
        }
    }

    // Helper class to hold question and answer

}
