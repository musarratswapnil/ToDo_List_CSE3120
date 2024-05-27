package com.example.todo_list.App_Options;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

public class SettingsActivity extends AppCompatActivity {

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

    private void setupButtonListeners(LinearLayout button, Class<?> activityClass) {
        button.setOnClickListener(v -> {
            Intent intent = SettingsFactory.createIntent(SettingsActivity.this, activityClass);
            startActivity(intent);
        });
    }
}
