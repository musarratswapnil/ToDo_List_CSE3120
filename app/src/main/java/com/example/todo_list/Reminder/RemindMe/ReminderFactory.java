package com.example.todo_list.Reminder.RemindMe;

public class ReminderFactory {
    public  ReminderInterface getReminder(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Reminder type cannot be null");
        }

        if (type.equalsIgnoreCase("call")) {
            return new CallReminder();

        }
       else if (type.equalsIgnoreCase("alarm")) {
        return new AlarmReminder();
       }else if (type.equalsIgnoreCase("sms")) {
            return new SMSReminder();
        } else {
            throw new IllegalArgumentException("Unknown reminder type: " + type);
        }
    }
}

