/**
 * The HelpAdapter class is responsible for managing the RecyclerView that displays help items.
 */
package com.example.todo_list.App_Options.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todo_list.R;
import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {
    private List<Help> helpItems;

    /**
     * Constructs a HelpAdapter with the provided list of help items.
     *
     * @param helpItems The list of help items to be displayed.
     */
    public HelpAdapter(List<Help> helpItems) {
        this.helpItems = helpItems;
    }

    /**
     * Creates new views (invoked by the layout manager).
     *
     * @param parent   The parent view group.
     * @param viewType The view type of the new View.
     * @return A new HelpViewHolder that holds a View.
     */
    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_item, parent, false);
        return new HelpViewHolder(itemView);
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager).
     *
     * @param holder   The HelpViewHolder instance representing the item view.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {
        Help currentItem = helpItems.get(position);
        holder.questionTextView.setText(currentItem.getQuestion());
        holder.answerTextView.setText(currentItem.getAnswer());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return helpItems.size();
    }

    /**
     * The HelpViewHolder class holds references to the views for a single item in the RecyclerView.
     */
    static class HelpViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView, answerTextView;

        /**
         * Constructs a new HelpViewHolder.
         *
         * @param itemView The View for a single item in the RecyclerView.
         */
        HelpViewHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.question);
            answerTextView = itemView.findViewById(R.id.answer);
        }
    }
}
