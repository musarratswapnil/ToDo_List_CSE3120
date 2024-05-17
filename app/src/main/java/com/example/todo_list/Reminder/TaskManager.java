package com.example.todo_list.Reminder;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
public class TaskManager {
    private DatabaseReference databaseReference;
    private Context context;
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

    public TaskManager(Context context) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void saveTask(String userId,String title, String date, String time,String content, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
              Task task = new Task(title, date, time, content);

        DatabaseReference userTasksRef = databaseReference.child("users").child(userId).child("tasks");
        String taskId = userTasksRef.push().getKey();
        userTasksRef.child(taskId).setValue(task)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    @SuppressLint("ScheduleExactAlarm")
    public void scheduleReminder(long triggerAtMillis, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }
}
