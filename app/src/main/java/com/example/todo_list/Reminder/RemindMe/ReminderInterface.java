package com.example.todo_list.Reminder.RemindMe;

import android.content.Context;

public interface ReminderInterface {
    public void setReminder(Context context,  int year, int month, int day, int hour, int minute, String title, String content,String Phone,int requestCode);
}
