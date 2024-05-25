package com.example.todo_list.App_Options;



import android.content.Context;
import android.content.Intent;

public class SettingsFactory {

    public static Intent createIntent(Context context, Class<?> activityClass) {
        return new Intent(context, activityClass);
    }
}
