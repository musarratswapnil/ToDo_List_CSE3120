package com.example.todo_list.CgpaCalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.R;

import java.util.ArrayList;
/**
 * This adapter binds semester data to the RecyclerView in the CGPA calculator activity.
 */

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder> {

    private ArrayList<CgpaActivity.Semester> semesters;
    /**
     * Constructor for SemesterAdapter.
     * @param semesters A list of semesters to be displayed in the RecyclerView.
     */
    public SemesterAdapter(ArrayList<CgpaActivity.Semester> semesters) {
        this.semesters = semesters;
    }
    /**
     * Called when RecyclerView needs a new {@link SemesterViewHolder} of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new SemesterViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semester_item, parent, false);
        return new SemesterViewHolder(view);
    }
    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method updates the contents of the {@link SemesterViewHolder#itemView} to reflect the item at the given position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        CgpaActivity.Semester semester = semesters.get(position);
        holder.semesterNameTextView.setText("Semester: " + semester.name);
        holder.gpaTextView.setText(semester.gpa);

        // Clear previous rows except the header
        holder.semesterTable.removeViews(1, holder.semesterTable.getChildCount() - 1);

        // Add rows for each course, credit, and grade
        for (int i = 0; i < semester.courses.size(); i++) {
            TableRow row = new TableRow(holder.itemView.getContext());

            TextView courseTextView = new TextView(holder.itemView.getContext());
            courseTextView.setText(semester.courses.get(i));
            row.addView(courseTextView);

            TextView creditTextView = new TextView(holder.itemView.getContext());
            creditTextView.setText(String.valueOf(semester.credits.get(i)));
            row.addView(creditTextView);

            TextView gradeTextView = new TextView(holder.itemView.getContext());
            gradeTextView.setText(String.valueOf(semester.grades.get(i)));
            row.addView(gradeTextView);

            holder.semesterTable.addView(row);
        }
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return semesters.size();
    }
    /**
     * ViewHolder class that holds the views for each semester item.
     */
    public static class SemesterViewHolder extends RecyclerView.ViewHolder {
        TextView semesterNameTextView, gpaTextView;
        TableLayout semesterTable;
        /**
         * Constructor for SemesterViewHolder.
         * @param itemView The view of the individual semester item.
         */
        public SemesterViewHolder(@NonNull View itemView) {
            super(itemView);
            semesterNameTextView = itemView.findViewById(R.id.semester_name_text_view);
            gpaTextView = itemView.findViewById(R.id.gpa_text_view);
            semesterTable = itemView.findViewById(R.id.semester_table);
        }
    }
}

