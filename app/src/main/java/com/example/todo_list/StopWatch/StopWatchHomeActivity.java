package com.example.todo_list.StopWatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.todo_list.App_Options.FragmentFactory;
import com.example.todo_list.R;

public class StopWatchHomeActivity extends AppCompatActivity {
    Button buttonOpenStopWatchFragment;
    Button buttonOpenTimerFragment;

    private static final byte STATUS_STOP_WATCH = 1;
    private static final byte STATUS_TIMER = 2;
    private byte fragmentStatus;

    private StopwatchFragment stopwatchFragment;
    private TimerFragment timerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch_home);
        configure();
    }

    private void configure() {
        findViewsById();
        setOnClickListeners();
        initializing();
    }

    private void findViewsById() {
        buttonOpenStopWatchFragment = findViewById(R.id.button_open_stop_watch);
        buttonOpenTimerFragment = findViewById(R.id.button_open_timer);
    }

    private void setOnClickListeners() {
        buttonOpenStopWatchFragment.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container2, stopwatchFragment)
                    .commit();
            fragmentStatus = STATUS_STOP_WATCH;
        });
        buttonOpenTimerFragment.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container2, timerFragment)
                    .commit();
            fragmentStatus = STATUS_TIMER;
        });
    }

    private void initializing() {
        stopwatchFragment = (StopwatchFragment) FragmentFactory.createFragment(FragmentFactory.STATUS_STOP_WATCH);
        timerFragment = (TimerFragment) FragmentFactory.createFragment(FragmentFactory.STATUS_TIMER);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container2, stopwatchFragment).commit();
        fragmentStatus = STATUS_STOP_WATCH;
    }

}
