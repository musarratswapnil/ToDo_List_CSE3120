package com.example.todo_list.Reminder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.todo_list.LoginSignup.LoginActivity;
import com.example.todo_list.Note.FirebaseDatabaseSingleton;
import com.example.todo_list.R;
import com.example.todo_list.Reminder.RemindMe.ReminderFactory;
import com.example.todo_list.Reminder.RemindMe.ReminderInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.Locale;

public class AddTaskFragment extends Fragment {
    private TextInputEditText titleEditText;
    private TextView dateTextView;
    private ImageView calendarImageView;
    private TextView timeTextView;
    private ImageView clockImageView;
    private TextInputEditText contentEditText,phoneNumberEditText;
    private TextInputLayout phoneNumberInputLayout;
    private Button saveButton;
    private String taskId,phone;
    private ReminderInterface reminder;
    private RadioGroup notificationTypeGroup;
    private static final int SMS_PERMISSION_REQUEST_CODE = 100;
    public class Task {
        private String title;
        private String content;
        private String date;
        private String time;
        private int requestCode;
        private String reminderType;

        public Task() {
        }

        public Task(String title, String date, String time, String content, String reminderType,int requestCode) {
            this.title = title;
            this.date = date;
            this.time = time;
            this.content = content;
           this.requestCode = requestCode;
            this.reminderType = reminderType;
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
        public int getRequestCode() {
            return requestCode;
        }

        public String getReminderType() {
            return reminderType;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    // Declare variables to store selected date and time
    private int year, month, day, hour, minute;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        setupRadioGroupListener();
        setupDateClickListener();
        setupTimeClickListener();
        setupSaveClickListener();

    }


    @SuppressLint("ScheduleExactAlarm")
    private void saveTaskToFirebase(String title, String date, String time, String content,String phone) {
        // Get the current user ID (assuming you have implemented Firebase Authentication)
         FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
       if (currentUser == null) {
            // User is not authenticated, handle accordingly
            Toast.makeText(getActivity(), "Not logged in!", Toast.LENGTH_SHORT).show();
            // Redirect to login page
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
       }
           String userId = currentUser.getUid();

        // Create a reference to the user's tasks node
        DatabaseReference userTasksRef = FirebaseDatabaseSingleton.getInstance().getReference("users")
                .child(userId)
                .child("tasks");

        // Generate a unique key for the task

        // Create a Task object

        Calendar calendar = Calendar.getInstance();
        int requestCode=(int)(calendar.getTimeInMillis()% Integer.MAX_VALUE);

        reminder = new ReminderFactory().getReminder(getSelectedReminderType());
        reminder.setReminder(getContext(), year, month, day, hour, minute, title, content,phone,requestCode);

//        Calendar calendar = Calendar.getInstance();
//        int requestCode=(int)(calendar.getTimeInMillis()% Integer.MAX_VALUE);

        Task task = new Task(title, date, time, content, getSelectedReminderType(),requestCode);
        saveTaskToFirebase(userTasksRef, task);

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

    private void initializeViews(View view) {
        // Find the views
        titleEditText = view.findViewById(R.id.titleEditText);
        dateTextView = view.findViewById(R.id.dateTextView);
        calendarImageView = view.findViewById(R.id.calendarImageView);
        timeTextView = view.findViewById(R.id.timeTextView);
        clockImageView = view.findViewById(R.id.clockImageView);
        contentEditText = view.findViewById(R.id.contentEditText);
        saveButton = view.findViewById(R.id.saveButton);
        notificationTypeGroup = view.findViewById(R.id.notificationTypeGroup);
        phoneNumberInputLayout=view.findViewById(R.id.phoneNumberInputLayout);
        phoneNumberEditText=view.findViewById(R.id.phoneNumberEditText);

    }


    private void setupTimeClickListener() {
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
                            AddTaskFragment.this.year = year;
                            month = monthOfYear;
                            day = dayOfMonth;


                            // Display the selected date
                            String selectedDateStr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            dateTextView.setText(selectedDateStr);
                        }
                    }
                }, currentYear, currentMonth, currentDay);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });
    }

    private void setupSaveClickListener() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String date = dateTextView.getText().toString().trim();
                String time = timeTextView.getText().toString().trim();
                String content = contentEditText.getText().toString().trim();
                String phoneNumber = phoneNumberEditText.getText().toString().trim();


                // Validate the input
                if (phoneNumberInputLayout.getVisibility() == View.VISIBLE && TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getActivity(), "Phone number is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(date) || TextUtils.isEmpty(time)) {
                    // Show error message if title, date, or time is empty
                    Toast.makeText(getActivity(), "Please enter both title, date and time", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if content exceeds the maximum number of lines
                    int lineCount = contentEditText.getLineCount();
                    if (lineCount > 3) {
                        // Show an error message to the user
                        Toast.makeText(getActivity(), "Content should not exceed 3 lines/30 words", Toast.LENGTH_SHORT).show();
                    } else {
                        // Get the current time
                        final Calendar currentTime = Calendar.getInstance();

                        // Get the selected date and time
                        Calendar selectedDateTime = Calendar.getInstance();
                        selectedDateTime.set(year, month, day, hour, minute);


                        // Calculate the time difference between the selected time and current time
                        long timeDifferenceInMillis = selectedDateTime.getTimeInMillis() - currentTime.getTimeInMillis();
                        int timeDifferenceInMinutes = (int) (timeDifferenceInMillis / (60 * 1000));
                        if (timeDifferenceInMinutes < 2) {
                            Toast.makeText(getActivity(), "Please select a time at least two minutes later", Toast.LENGTH_SHORT).show();
                        } else {

                         saveTaskToFirebase(title, date, time, content,phoneNumber);
                        }
                    }
                }
            }
        });
    }



    private void setupDateClickListener() {

        clockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current time
                final Calendar currentTime = Calendar.getInstance();

                // Create a TimePickerDialog to allow the user to select a time
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Create a calendar object with the selected time
                        Calendar selectedDateTime = Calendar.getInstance();
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);

                        // Combine selected date and time into a single calendar object
                        selectedDateTime.set(year, month, day); // Assuming year, month, and day are set elsewhere

                        // Compare the selected datetime with the current datetime
                        if (selectedDateTime.after(currentTime)) {
                            // The selected datetime is in the future
                            // Store the selected time in variables
                            hour = hourOfDay;
                            AddTaskFragment.this.minute = minute;

                            // Display the selected time
                            String selectedTimeStr = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                            timeTextView.setText(selectedTimeStr);
                        } else {

                            // The selected datetime is in the past or too close to the current datetime
                            Toast.makeText(getActivity(), "Please select a datetime in the future", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), true);

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });

    }

    private void saveTaskToFirebase(DatabaseReference userTasksRef, Task task) {
        taskId = userTasksRef.push().getKey();
        if (isNetworkConnected()) {
            userTasksRef.child(taskId).setValue(task)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getActivity(), "Task saved successfully", Toast.LENGTH_SHORT).show();
                        navigateToHomeFragment();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Failed to save task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            saveTaskLocally(taskId, task);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void saveTaskLocally(String taskId, Task task) {
        Toast.makeText(getActivity(), "Task saved locally. No internet connection.", Toast.LENGTH_SHORT).show();
        navigateToHomeFragment();
    }

    private String getSelectedReminderType() {
        int selectedId = notificationTypeGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.callOption) {

            return "call";
        } else if (selectedId == R.id.alarmOption) {

            return "alarm";
        } else if (selectedId == R.id.smsOption) {
            return "sms";
        } else {
            return "alarm";
        }
    }

    private void setupRadioGroupListener() {
        notificationTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Phone number input is only visible when the SMS option is selected
                if (checkedId == R.id.smsOption) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        // Request SMS sending permission
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
                    }

                    phoneNumberInputLayout.setVisibility(View.VISIBLE);
                }
                else  if (checkedId == R.id.callOption) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }

                    phoneNumberInputLayout.setVisibility(View.VISIBLE);
                }
                else {
                    phoneNumberInputLayout.setVisibility(View.GONE);
                }
            }
        });
    }

}
