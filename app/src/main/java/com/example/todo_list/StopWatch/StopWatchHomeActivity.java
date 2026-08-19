package com.example.todo_list.StopWatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

/**
 * Home activity for the stopwatch functionality.
 * This activity serves as the entry point for the stopwatch feature.
 */
public class StopWatchHomeActivity extends AppCompatActivity {

    // UI elements
    private Button buttonOpenStopWatch;
    private Button buttonOpenTimer;

    /**
     * Called when the activity is starting. Responsible for initializing the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch_home);

        // Initialize UI elements
        buttonOpenStopWatch = findViewById(R.id.button_open_stop_watch);
        buttonOpenTimer = findViewById(R.id.button_open_timer);

        // Set click listeners for buttons
        buttonOpenStopWatch.setOnClickListener(v -> {
            startActivity(new Intent(StopWatchHomeActivity.this, StopWatchActivity.class));
        });

        buttonOpenTimer.setOnClickListener(v -> {
            startActivity(new Intent(StopWatchHomeActivity.this, TimerActivity.class));
        });

        // Show default activity
        if (savedInstanceState == null) {
            startActivity(new Intent(StopWatchHomeActivity.this, StopWatchActivity.class));
        }
    }
}
