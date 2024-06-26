package com.example.todo_list.Reminder.RemindMe;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.todo_list.Broadcast.ReminderBroadcastReceiver;

import java.util.Calendar;

public class CallReminder implements ReminderInterface{
    int requestCode;
    @Override
    public void setReminder(Context context,  int year, int month, int day, int hour, int minute, String title, String content,String Phone,int requestCode) {
        Intent reminderIntent = new Intent(context, ReminderBroadcastReceiver.class);
        reminderIntent.putExtra("title", title);
        reminderIntent.putExtra("content", content);
        reminderIntent.putExtra("phoneNumber", Phone);
        reminderIntent.putExtra("isSMS", false);
        reminderIntent.putExtra("isCall", true);
        scheduleReminder(context, title, content, year, month, day, hour, minute,Phone,requestCode);

    }

    private void scheduleReminder(Context context,String title, String content,int year, int month, int day, int hour, int minute,String Phone,int requestCode) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        calendar.set(Calendar.SECOND, 0);


        long currentTimeMillis = System.currentTimeMillis();

        long timeDifference = calendar.getTimeInMillis()-currentTimeMillis;


        if (timeDifference > 0) {
            setCall(context, Phone, calendar.getTimeInMillis(),requestCode);
        } else {
            throw new IllegalArgumentException("Alarm time must be in the future.");
        }        }


    @SuppressLint("ScheduleExactAlarm")
    private void setCall(Context context, String phoneNumber, long triggerAtMillis,int requestCode) {
        Intent reminderIntent = new Intent(context, ReminderBroadcastReceiver.class);
        reminderIntent.putExtra("phoneNumber", phoneNumber);
        reminderIntent.putExtra("isCall", true);
//         requestCode = (int) (triggerAtMillis % Integer.MAX_VALUE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, reminderIntent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }
}
