/**
 * The SettingsFactory class provides a factory method to create Intent objects for launching activities.
 */
package com.example.todo_list.App_Options;

import android.content.Context;
import android.content.Intent;

public class SettingsFactory {

    /**
     * Creates an Intent object for launching a specified activity.
     *
     * @param context      The context from which the activity is being launched.
     * @param activityClass The class of the activity to be launched.
     * @return An Intent object configured to launch the specified activity.
     */
    public static Intent createIntent(Context context, Class<?> activityClass) {
        return new Intent(context, activityClass);
    }
}
