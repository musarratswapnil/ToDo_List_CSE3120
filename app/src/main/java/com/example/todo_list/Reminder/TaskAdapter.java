package com.example.todo_list.Reminder;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.Broadcast.ReminderBroadcastReceiver;
import com.example.todo_list.R;
import com.example.todo_list.Reminder.Sort.SortingStrategy;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private DatabaseReference tasksRef;
    private Context context;
    private FragmentManager fragmentManager;
    private SortingStrategy sortingStrategy;

//    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//    Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
//    PendingIntent pendingIntent;
    public TaskAdapter(List<Task> taskList, DatabaseReference tasksRef, Context context,FragmentManager fragmentManager) {
        this.taskList = taskList;
        this.tasksRef = tasksRef;
        this.context = context;
        this.fragmentManager = fragmentManager;
//        this.sortingStrategy = new SortByDateStrategy(); // Default sorting strategy
        refreshList();

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        int reversedPosition = getItemCount() - 1 - position;
        Task task = taskList.get(reversedPosition);
//         pendingIntent = PendingIntent.getBroadcast(context, task.getRequestCode(), intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_UPDATE_CURRENT);

        holder.bind(task);

        if (task.isOverdue()) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

//click on delete icon
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(task);
            }
        });
//click on edit icon
        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateTaskFragment updateTaskFragment = new UpdateTaskFragment();
                // Create a Bundle to pass data to the fragment
                Bundle bundle = new Bundle();

                // Put task information into the Bundle
                bundle.putString("taskId", task.getKey());

                // Set the arguments of the fragment
                updateTaskFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, updateTaskFragment)
                        .commit();

            }
        });
    }

    private void showDeleteConfirmationDialog(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    cancelAlarm(task, task.getRequestCode());

                    // Proceed to delete the task from Firebase
                    deleteTask(task);
                } catch (Exception e) {
                    // Log any exception
                    Log.e("requestCodeAlarm11", "Exception occurred: " + e.getMessage(), e);
                }

            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void deleteTask(Task task) {


//        tasksRef.child(task.getKey()).removeValue();
        tasksRef.child(task.getKey()).removeValue().addOnSuccessListener(aVoid -> {
            int index = taskList.indexOf(task);

//            if (index != -1) {
//                taskList.remove(index);
//                refreshList();
//                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
//            }
        }).addOnFailureListener(e -> {
            Toast.makeText(context, "Failed to delete task: " + e.getMessage(), Toast.LENGTH_LONG).show();
        });

    }

    private void cancelAlarm(Task task,int requestCode) {
        Log.e("requestCodeAlarm1", "requestCode: " + requestCode);

        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        Log.e("requestCodeAlarm6", "Created Intent for ReminderBroadcastReceiver");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent,    PendingIntent.FLAG_IMMUTABLE);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

    }
    public void setSortingStrategy(SortingStrategy strategy) {
        this.sortingStrategy = strategy;
        refreshList();
    }

//    private void cancelAlarm(Task task) {
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task.getKey().hashCode(), intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
//        if (pendingIntent != null && alarmManager != null) {
//            alarmManager.cancel(pendingIntent);
//        }
//    }

    private void refreshList() {
        if (sortingStrategy != null) {
            sortingStrategy.sort(taskList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView monthTextView;
        private TextView dateTextView;
        private TextView dateInitTextView;
        private TextView yearTextView;
        private TextView titleTextView;
        private ImageView deleteImageView;
        private ImageView editImageView;

        private TextView contentTextView;
        private TextView timeTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            monthTextView = itemView.findViewById(R.id.monthTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            dateInitTextView = itemView.findViewById(R.id.dateInitTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            editImageView = itemView.findViewById(R.id.editImageView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }

        public void bind(Task task) {
            String month = getMonthString(task.getMonth());
            String day = String.valueOf(task.getDay());
            String date = String.valueOf(task.getDate());
            String year = String.valueOf(task.getYear());
            monthTextView.setText(month);
            dateTextView.setText(day);
            dateInitTextView.setText(date);
            yearTextView.setText(year);
            titleTextView.setText(task.getTitle());
            contentTextView.setText(task.getContent());
            timeTextView.setText(task.getTime());


        }
        private String getMonthString(int month) {
            String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            if (month >= 1 && month <= 12) {
                return monthArray[month - 1];
            } else {
                return "";
            }
        }


    }
}