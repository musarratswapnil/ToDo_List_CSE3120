package com.example.todo_list.CgpaCalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todo_list.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

/**
 * Adapter class for displaying and managing semester data in a RecyclerView.
 */
public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder> {

    private ArrayList<CgpaActivity.Semester> semesters;
    private DatabaseReference semestersRef;

    /**
     * Constructor for the SemesterAdapter.
     *
     * @param semesters    List of semesters to display.
     * @param semestersRef Firebase database reference for semesters.
     */
    public SemesterAdapter(ArrayList<CgpaActivity.Semester> semesters, DatabaseReference semestersRef) {
        this.semesters = semesters;
        this.semestersRef = semestersRef;
    }

    /**
     * Creates a new SemesterViewHolder.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type of the new View.
     * @return A new SemesterViewHolder.
     */
    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semester_item, parent, false);
        return new SemesterViewHolder(view);
    }

    /**
     * Binds data to the SemesterViewHolder.
     *
     * @param holder   The SemesterViewHolder.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        CgpaActivity.Semester semester = semesters.get(position);
        holder.semesterNameTextView.setText("Semester: " + semester.name);
        holder.gpaTextView.setText(semester.gpa);

        holder.semesterTable.removeViews(1, holder.semesterTable.getChildCount() - 1);

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

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(holder.itemView.getContext(), semester);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        return semesters.size();
    }

    /**
     * ViewHolder class for displaying semester data.
     */
    public static class SemesterViewHolder extends RecyclerView.ViewHolder {
        TextView semesterNameTextView, gpaTextView;
        TableLayout semesterTable;
        Button deleteButton;

        /**
         * Constructor for the SemesterViewHolder.
         *
         * @param itemView The view of the item.
         */
        public SemesterViewHolder(@NonNull View itemView) {
            super(itemView);
            semesterNameTextView = itemView.findViewById(R.id.semester_name_text_view);
            gpaTextView = itemView.findViewById(R.id.gpa_text_view);
            semesterTable = itemView.findViewById(R.id.semester_table);
            deleteButton = itemView.findViewById(R.id.delete_semester_button);
        }
    }

    /**
     * Shows a confirmation dialog for deleting a semester.
     *
     * @param context  The context.
     * @param semester The semester to delete.
     */
    private void showDeleteConfirmationDialog(Context context, CgpaActivity.Semester semester) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Semester")
                .setMessage("Are you sure you want to delete this semester?")
                .setPositiveButton("Yes", (dialog, which) -> deleteSemester(context, semester))
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Deletes a semester from Firebase and updates the adapter.
     *
     * @param context  The context.
     * @param semester The semester to delete.
     */
    private void deleteSemester(Context context, CgpaActivity.Semester semester) {
        semestersRef.child(semester.name).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    semesters.remove(semester);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Semester deleted successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete semester. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
   });
}
}
