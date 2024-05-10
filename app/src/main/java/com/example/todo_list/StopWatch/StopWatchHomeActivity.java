package com.example.todo_list.StopWatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    private void configure()
    {
        findViewsById();
        setOnClickListeners();
        initializing();
    }

    private void findViewsById()
    {
        buttonOpenStopWatchFragment = findViewById(R.id.button_open_stop_watch);
        buttonOpenTimerFragment = findViewById(R.id.button_open_timer);
    }

    private void setOnClickListeners()
    {
        buttonOpenStopWatchFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container2, stopwatchFragment)
                        .commit();
            }
        });
        buttonOpenTimerFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerFragment=new TimerFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container2, timerFragment)
                        .commit();
            }
        });
    }

    private void initializing()
    {
        stopwatchFragment = new StopwatchFragment();
        timerFragment = new TimerFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container2,stopwatchFragment).commit();
        buttonOpenTimerFragment.setTextColor(getResources().getColor(R.color.DarkRed));
        fragmentStatus = STATUS_TIMER;
    }
}

