package com.example.todo_list.Reminder.RemindMe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.todo_list.Broadcast.ReminderBroadcastReceiver;

import java.util.Calendar;

public class AlarmReminder implements ReminderInterface{

    int requestCode;
private void scheduleReminder(Context context,String title, String content,int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        calendar.set(Calendar.SECOND, 0);


        long currentTimeMillis = System.currentTimeMillis();

        long timeDifference = calendar.getTimeInMillis()-currentTimeMillis;


    if (timeDifference > 0) {
        setAlarm(context, title, content, calendar.getTimeInMillis());
    } else {
        throw new IllegalArgumentException("Alarm time must be in the future.");
    }        }
private void setAlarm(Context context, String title, String content, long triggerAtMillis) {
        // Create an Intent to trigger the ReminderBroadcastReceiver
        Intent reminderIntent = new Intent(context, ReminderBroadcastReceiver.class);
        reminderIntent.putExtra("title", title);
        reminderIntent.putExtra("content", content);
        // Create a PendingIntent to wrap the reminderIntent
     requestCode = (int) (triggerAtMillis % Integer.MAX_VALUE);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
        Intent intent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
        context.startActivity(intent);
        return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else {
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
        }

    @Override
    public void setReminder(Context context,  int year, int month, int day, int hour, int minute, String title, String content,String Phone) {
        Intent reminderIntent = new Intent(context, ReminderBroadcastReceiver.class);
        reminderIntent.putExtra("title", title);
        reminderIntent.putExtra("content", content);
        reminderIntent.putExtra("phoneNumber", Phone);
        reminderIntent.putExtra("isSMS", false);
        reminderIntent.putExtra("isCall", false);
    scheduleReminder(context, title, content, year, month, day, hour, minute);

    }
}