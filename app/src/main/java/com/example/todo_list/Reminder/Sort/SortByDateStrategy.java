package com.example.todo_list.Reminder.Sort;


import com.example.todo_list.Reminder.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SortByDateStrategy implements SortingStrategy {
    @Override
    public void sort(List<Task> list) {
        Collections.sort(list, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                // Parse the dates of the tasks
                Calendar calendar1 = getParsedDateTime(task1.getDate(), task1.getTime());
                Calendar calendar2 = getParsedDateTime(task2.getDate(), task2.getTime());
                if (calendar1 != null && calendar2 != null) {
                    int dateComparison = calendar2.compareTo(calendar1);
                    if (dateComparison == 0) {
                        // If dates are the same, compare by time
                        int timeComparison = compareTime(task2.getTime(), task1.getTime());
                        return timeComparison;
                    } else {
                        return dateComparison;
                    }
                } else {
                    return 0;
                }
            }
        });
    }
    private Calendar getParsedDateTime(String date, String time) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        try {
            if (date != null && time != null) {
                calendar.setTime(sdf.parse(date + " " + time));
                return calendar;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int compareTime(String time1, String time2) {
        if (time1 != null && time2 != null) {
            return time1.compareTo(time2);
        } else {
            return 0;
        }
    }
}
