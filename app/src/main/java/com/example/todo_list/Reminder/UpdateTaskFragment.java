package com.example.todo_list.Reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todo_list.R;
import com.example.todo_list.Broadcast.ReminderBroadcastReceiver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;


public class UpdateTaskFragment extends Fragment {
    private String taskId;
//    // Declare the necessary views and Firebase variables
    public class Task {
        private String title;
        private String content;
        private String date;
        private String time;


        public Task() {
            // Default constructor required for Firebase
        }
        public Task(String title, String date, String time,String content) {
            this.title = title;
            this.date = date;
            this.time = time;
            this.content = content;
        }
        public String getTitle() {
            return title;
        }
        public String getContent() {
            return content;
        }
        public String getDate() {
            return date;
        }
        public String getTime() {
            return time;
        }
    }
    String title;
    String timeText ;
    String dateText;
    String content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle updatedInstanceState) {
        // Inflating the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_updatetask, container, false);


        taskId = getArguments().getString("taskId");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference taskRef = rootRef.child("users").child("1").child("tasks").child(taskId);

        taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve the title value
                    title = dataSnapshot.child("title").getValue(String.class);
                    timeText = dataSnapshot.child("time").getValue(String.class);
                     dateText = dataSnapshot.child("date").getValue(String.class);
                    content = dataSnapshot.child("content").getValue(String.class);
                    // Find the views
                    TextInputEditText titleEditText = view.findViewById(R.id.titleEditText);
                    TextView dateTextView = view.findViewById(R.id.dateTextView);
                    ImageView calendarImageView = view.findViewById(R.id.calendarImageView);
                    TextView timeTextView = view.findViewById(R.id.timeTextView);
                    ImageView clockImageView = view.findViewById(R.id.clockImageView);
                    TextInputEditText contentEditText = view.findViewById(R.id.contentEditText);

            //Retrived data show in textbox
                    titleEditText.setText(title);
                    timeTextView.setText(timeText);
                    dateTextView.setText(dateText);
                    contentEditText.setText(content);


                    Log.d("Task Title", title);
                } else {
                    Log.d("Task Title", "Task not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Error retrieving the task: " + databaseError.getMessage());
            }
        });

        return view;
    }


    // Declare variables to store selected date and time
    private int year, month, day, hour, minute;
   private String selectedDateStr,selectedTimeStr;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle updatedInstanceState) {
        super.onViewCreated(view, updatedInstanceState);

        // Find the views
        TextInputEditText titleEditText = view.findViewById(R.id.titleEditText);
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        ImageView calendarImageView = view.findViewById(R.id.calendarImageView);
        TextView timeTextView = view.findViewById(R.id.timeTextView);
        ImageView clockImageView = view.findViewById(R.id.clockImageView);
        TextInputEditText contentEditText = view.findViewById(R.id.contentEditText);
        Button updateButton = view.findViewById(R.id.updateButton);

        // Set click listener for the calendar image view
        calendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                final Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog to allow the user to select a date
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.e("onDateset", ""+dayOfMonth);

             if(dateText != null) {
                 String[] dateParts = dateText.split("/");
                 Log.e("dateParts", "onDateSet: " + dateText);
                 if (year != Integer.parseInt(dateParts[2]) && monthOfYear != Integer.parseInt(dateParts[1]) && dayOfMonth != Integer.parseInt(dateParts[0])) {
                     dayOfMonth = Integer.parseInt(dateParts[0]);
                     monthOfYear = Integer.parseInt(dateParts[1]) - 1; // Subtracting 1 bcz Calendar months are zero-based
                     year = Integer.parseInt(dateParts[2]);
                 }
             }
                        // Create a Calendar object with the selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        // Get the current date
                        Calendar currentDate = Calendar.getInstance();

                        // Compare the selected date with the current date
                        if (selectedDate.before(currentDate)) {
                            // Selected date is in the past, show an error message
                            Toast.makeText(getActivity(), "Please select a date in the future", Toast.LENGTH_SHORT).show();
                        } else {
                            // Store the selected date in variables
                            UpdateTaskFragment.this.year = year;
                            UpdateTaskFragment.this.month = monthOfYear;
                            UpdateTaskFragment.this.day = dayOfMonth;


                            // Display the selected date
                            selectedDateStr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            dateTextView.setText(selectedDateStr);
                        }
                    }
                }, currentYear, currentMonth, currentDay);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });


        // Set click listener for the clock image view

        clockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current time
                final Calendar currentTime = Calendar.getInstance();

                // Create a TimePickerDialog to allow the user to select a time
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar selectedDateTime = Calendar.getInstance();
                        Log.e("onTimeset", "" + hourOfDay);

//                        if (timeText != null) {
//                            String[] timeparts = timeText.split(":");
//                            Log.e("timeParts", "onTimeset: " + timeText);
//
//                            if (!String.format(Locale.getDefault(), "%02d", hourOfDay).equals(timeparts[0].substring(0, 2)) ||
//                                    !String.format(Locale.getDefault(), "%02d", minute).equals(timeparts[1].substring(0, 2))) {
//                                // If different, set hourOfDay and minute to the existing time's hour and minute
//                                hourOfDay = Integer.parseInt(timeparts[0]);
//                                minute = Integer.parseInt(timeparts[1]);
//                            }
//                        }

                        Log.e("onTimeset3", "" + hourOfDay);

                        // Create a calendar object with the selected time
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);

                        // Combine selected date and time into a single calendar object
                        if(dateText != null && selectedDateStr==null) {
                            String[] dateParts = dateText.split("/");
                            Log.e("dateParts", "onDateSet: " + dateText);
                                UpdateTaskFragment.this.day = Integer.parseInt(dateParts[0]);
                            UpdateTaskFragment.this.month = Integer.parseInt(dateParts[1]) - 1; // Subtracting 1 bcz Calendar months are zero-based
                            UpdateTaskFragment.this.year = Integer.parseInt(dateParts[2]);

                        }
                        selectedDateTime.set(year, month, day);
                        // Compare the selected datetime with the current datetime
                        if (selectedDateTime.after(currentTime)) {
                            // The selected datetime is in the future
                            // Store the selected time in variables
                            UpdateTaskFragment.this.hour = hourOfDay;
                            UpdateTaskFragment.this.minute = minute;

                            // Display the selected time
                          selectedTimeStr = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                            timeTextView.setText(selectedTimeStr);
                        } else {
                            // The selected datetime is in the past or too close to the current datetime
                            Toast.makeText(getActivity(), "Please select a time in the future", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), true);

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });

        // Set click listener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String date = dateTextView.getText().toString().trim();
                String time = timeTextView.getText().toString().trim();
                String content = contentEditText.getText().toString().trim();

                // Validate the input
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(date) || TextUtils.isEmpty(time)) {
                    // Show error message if title, date, or time is empty
                    Toast.makeText(getActivity(), "Please enter both title, date and time", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Check if content exceeds the maximum number of lines
                    int lineCount = contentEditText.getLineCount();
                    if (lineCount > 3) {
                        // Show an error message to the user
                        Toast.makeText(getActivity(), "Content should not exceed 3 lines/30 words", Toast.LENGTH_SHORT).show();
                    }else{
                        // Get the current time
                        final Calendar currentTime = Calendar.getInstance();

                        // Get the selected date and time
                        Calendar selectedDateTime = Calendar.getInstance();
                        selectedDateTime.set(year, month, day, hour, minute);
                       //compare current date
                        boolean isCurrentDate = selectedDateTime.get(Calendar.YEAR) == currentTime.get(Calendar.YEAR) &&
                                selectedDateTime.get(Calendar.MONTH) == currentTime.get(Calendar.MONTH) &&
                                selectedDateTime.get(Calendar.DAY_OF_MONTH) == currentTime.get(Calendar.DAY_OF_MONTH);





                        // Calculate the time difference between the selected time and current time
                        long timeDifferenceInMillis = selectedDateTime.getTimeInMillis() - currentTime.getTimeInMillis();
                        int timeDifferenceInMinutes = (int) (timeDifferenceInMillis / (60 * 1000));


                            // Check if the selected time is at least two minutes later
                            if (isCurrentDate && timeDifferenceInMinutes < 2) {
                                // Show an error message to the user
                                Toast.makeText(getActivity(), "Please select a time at least two minutes later", Toast.LENGTH_SHORT).show();
                            } else {
                                // update the task to Firebase
                                updateTaskToFirebase(taskId, title, date, time, content);
                            }


                    }
                }
            }
        });
    }

    @SuppressLint("ScheduleExactAlarm")
    private void updateTaskToFirebase(String taskId, String title, String date, String time, String content) {
        // Get the current user ID (assuming you have implemented Firebase Authentication)
        // FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//       if (currentUser == null) {
//            // User is not authenticated, handle accordingly
//            Toast.makeText(getActivity(), "Not logged in!", Toast.LENGTH_SHORT).show();
//            // Redirect to login page
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//            return;
//       }
        //   String userId = currentUser.getUid();

        // Create a reference to the user's tasks node
        DatabaseReference userTasksRef = FirebaseDatabase.getInstance().getReference("users")
                .child("1")
                .child("tasks")
                .child(taskId);

        if(date==null){
            date=dateText;
        }
        if(time==null){
            time=timeText;
        }
        // Create a Task object
        Task task = new Task(title, date, time, content);

        // Check for internet connection availability
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // Internet connection is available, update the task to Firebase
            userTasksRef.setValue(task)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Task updated successfully
                            Toast.makeText(getActivity(), "Task updated successfully", Toast.LENGTH_SHORT).show();
                            navigateToHomeFragment();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error occurred while saving the task
                            Toast.makeText(getActivity(), "Failed to update task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // No internet connection, update the task locally and redirect to the homepage
            userTasksRef.child(taskId).setValue(task);
            Toast.makeText(getActivity(), "Task updated locally. No internet connection.", Toast.LENGTH_SHORT).show();
            navigateToHomeFragment();
        }

        if(dateText != null && selectedDateStr==null) {
            String[] dateParts = dateText.split("/");
            Log.e("dateParts", "onDateSet: " + dateText);
            UpdateTaskFragment.this.day = Integer.parseInt(dateParts[0]);
            UpdateTaskFragment.this.month = Integer.parseInt(dateParts[1]) - 1; // Subtracting 1 bcz Calendar months are zero-based
            UpdateTaskFragment.this.year = Integer.parseInt(dateParts[2]);

        }

        if(timeText!=null && selectedTimeStr==null){
            String[] timeparts = timeText.split(":");
                            Log.e("timeParts", "onTimeset: " + timeText);

            UpdateTaskFragment.this.hour = Integer.parseInt(timeparts[0]);
            UpdateTaskFragment.this.minute = Integer.parseInt(timeparts[1]);
        }
        // For the reminder...
        // Create a Calendar object with the selected date and time
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        calendar.set(Calendar.SECOND, 0); //ensure that the notification will trigger at the exact minute, without any seconds delay.

        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Calculate the time difference between the current time and the selected time
        long timeDifference = calendar.getTimeInMillis() - currentTimeMillis;

        // Create an Intent to trigger the ReminderBroadcastReceiver
        Intent reminderIntent = new Intent(getActivity(), ReminderBroadcastReceiver.class);
        reminderIntent.putExtra("title", title);
        reminderIntent.putExtra("content", content);

        // Create a PendingIntent to wrap the reminderIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, reminderIntent, PendingIntent.FLAG_IMMUTABLE);

        // Get the AlarmManager
//        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//
//        // Schedule the alarm
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, currentTimeMillis + timeDifference, pendingIntent);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimeMillis + timeDifference, pendingIntent);
//        } else {
//            alarmManager.set(AlarmManager.RTC_WAKEUP, currentTimeMillis + timeDifference, pendingIntent);
//        }
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

// Check for permission on Android 12 (API level 31) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return; // Do not proceed with setting the alarm until permission is granted
            }
        }

// Schedule the alarm with appropriate method based on Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, currentTimeMillis + timeDifference, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimeMillis + timeDifference, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, currentTimeMillis + timeDifference, pendingIntent);
        }

    }

    private void navigateToHomeFragment() {
        // Implement the navigation logic to go back to HomeFragment
        // Example: use a FragmentManager to replace the current fragment with HomeFragment
        // Create an instance of the HomeFragment
        HomeFragment homeFragment = new HomeFragment();

        // Get the FragmentManager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Replace the current fragment with HomeFragment
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit();
    }
}
