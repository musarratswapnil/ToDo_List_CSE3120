/**
 * The SettingsActivity class represents the activity for managing application settings.
 */
package com.example.todo_list.App_Options;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todo_list.R;

public class SettingsActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. Initializes the activity and sets its content view, and sets up button listeners for various settings options.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        LinearLayout accountButton = findViewById(R.id.accountButton);
        LinearLayout privacyButton = findViewById(R.id.privacyButton);
        LinearLayout helpButton = findViewById(R.id.helpButton);
        LinearLayout aboutButton = findViewById(R.id.aboutButton);

        setupButtonListeners(accountButton, AccountActivity.class);
        setupButtonListeners(privacyButton, PrivacyActivity.class);
        setupButtonListeners(helpButton, HelpActivity.class);
        setupButtonListeners(aboutButton, AboutActivity.class);
    }

    /**
     * Sets up a button listener for a specific settings option.
     *
     * @param button        The LinearLayout representing the button for the settings option.
     * @param activityClass The class of the activity to be launched when the button is clicked.
     */
    private void setupButtonListeners(LinearLayout button, Class<?> activityClass) {
        button.setOnClickListener(v -> {
            Intent intent = SettingsFactory.createIntent(SettingsActivity.this, activityClass);
            startActivity(intent);
        });
    }
}
