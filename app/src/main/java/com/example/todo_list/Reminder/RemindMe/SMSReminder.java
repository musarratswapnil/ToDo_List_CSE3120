package com.example.todo_list.Reminder.RemindMe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.todo_list.Broadcast.ReminderBroadcastReceiver;

import java.util.Calendar;

public class SMSReminder implements ReminderInterface{
    public static final int SMS_PERMISSION_REQUEST_CODE = 100;
    int requestCode;
    @Override
    public void setReminder(Context context,  int year, int month, int day, int hour, int minute, String title, String content,String Phone,int requestCode) {
        Intent reminderIntent = new Intent(context, ReminderBroadcastReceiver.class);
        reminderIntent.putExtra("title", title);
        reminderIntent.putExtra("content", content);
        reminderIntent.putExtra("phoneNumber", Phone);
        reminderIntent.putExtra("isSMS", true);
        reminderIntent.putExtra("isCall", false);
            // Permission already granted, schedule the reminder
            scheduleReminder(context, title, content, year, month, day, hour, minute,Phone,requestCode);
           }
    private void scheduleReminder(Context context,String title, String content,int year, int month, int day, int hour, int minute,String Phone,int requestCode) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        calendar.set(Calendar.SECOND, 0);
        long currentTimeMillis = System.currentTimeMillis();
        long timeDifference = calendar.getTimeInMillis()-currentTimeMillis;
        if (timeDifference > 0) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                // Request permission
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
            } else {
                setSMS(context, Phone,content, calendar.getTimeInMillis(),requestCode);
            }
        } else {
            throw new IllegalArgumentException("Alarm time must be in the future.");
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setSMS(Context context, String phoneNumber, String message, long triggerAtMillis,int requestCode) {
        Intent reminderIntent = new Intent(context, ReminderBroadcastReceiver.class);
        reminderIntent.putExtra("phoneNumber", phoneNumber);
        reminderIntent.putExtra("message", message);
        reminderIntent.putExtra("isSMS", true);
//         requestCode = (int) (triggerAtMillis % Integer.MAX_VALUE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }
}