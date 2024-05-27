package com.example.todo_list.StopWatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

public class StopWatchHomeActivity extends AppCompatActivity {
    private Button buttonOpenStopWatch;
    private Button buttonOpenTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch_home);

        buttonOpenStopWatch = findViewById(R.id.button_open_stop_watch);
        buttonOpenTimer = findViewById(R.id.button_open_timer);

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
