package com.example.todo_list.StopWatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

public class StopWatchActivity extends AppCompatActivity implements View.OnClickListener {

    private static final byte STATE_INITIAL = 0;
    private static final byte STATE_START = 1;
    private static final byte STATE_STOP = 2;
    private static final byte STATE_FINISHED = 3;

    private static final long TIMER_HAS_NOT_STARTED_YET = -1;
    private static final long LONG_DURATION_FOR_TIMER = 3_660_099;

    private long tenMilliSecondsRemaining = TIMER_HAS_NOT_STARTED_YET;
    private byte stopWatchState = STATE_INITIAL;

    private Button buttonStartStopWatch;
    private Button buttonStopStopWatch;
    private Button buttonResumeStopWatch;
    private Button buttonResetStopWatch;

    private TextView textViewStopWatchHours;
    private TextView textViewStopWatchMinutes;
    private TextView textViewStopWatchSeconds;
    private TextView textViewStopWatchTenSeconds;

    private CountDownTimer countDownTimer;

    private Button buttonOpenStopWatch;
    private Button buttonOpenTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        configure();

        buttonOpenStopWatch = findViewById(R.id.button_open_stop_watch);
        buttonOpenTimer = findViewById(R.id.button_open_timer);

        buttonOpenStopWatch.setOnClickListener(v -> {
            // Do nothing as we are already in StopWatchActivity
        });

        buttonOpenTimer.setOnClickListener(v -> {
            startActivity(new Intent(StopWatchActivity.this, TimerActivity.class));
        });
    }



    private void configure() {
        findViewByIds();
        setOnClickListeners();
        goToSuitableState();
    }

    private void findViewByIds() {
        buttonStartStopWatch = findViewById(R.id.button_start_stop_watch);
        buttonStopStopWatch = findViewById(R.id.button_stop_stop_watch);
        buttonResumeStopWatch = findViewById(R.id.button_resume_stop_watch);
        buttonResetStopWatch = findViewById(R.id.button_reset_stop_watch);

        textViewStopWatchHours = findViewById(R.id.textView_stopwatch_h);
        textViewStopWatchMinutes = findViewById(R.id.textView_stopwatch_m);
        textViewStopWatchSeconds = findViewById(R.id.textView_stopwatch_s);
        textViewStopWatchTenSeconds = findViewById(R.id.textView_stopwatch_10ms);
    }

    private void setOnClickListeners() {
        buttonStartStopWatch.setOnClickListener(this);
        buttonStopStopWatch.setOnClickListener(this);
        buttonResumeStopWatch.setOnClickListener(this);
        buttonResetStopWatch.setOnClickListener(this);
    }

    private void goToSuitableState() {
        switch (stopWatchState) {
            case STATE_INITIAL:
                configInitialState();
                break;
            case STATE_START:
                configStartState();
                break;
            case STATE_STOP:
                configStopState();
                break;
            case STATE_FINISHED:
                // Do nothing
                break;
        }
    }

    protected void countDownTimerCombine(long remainingSecondsStatus) {
        long duration = LONG_DURATION_FOR_TIMER;
        if (remainingSecondsStatus != TIMER_HAS_NOT_STARTED_YET) {
            duration = remainingSecondsStatus;
        }

        countDownTimer = new CountDownTimer(duration, 10) {
            @Override
            public void onTick(long remainingTime) {
                updateTimerUI(remainingTime);
            }

            @Override
            public void onFinish() {
                stopWatchState = STATE_FINISHED;
                configInitialState();
            }
        };
        countDownTimer.start();
    }

    private void configInitialState() {
        buttonStartStopWatch.setVisibility(View.VISIBLE);
        buttonStopStopWatch.setVisibility(View.GONE);
        buttonResumeStopWatch.setVisibility(View.GONE);
        buttonResetStopWatch.setVisibility(View.GONE);
        tenMilliSecondsRemaining = TIMER_HAS_NOT_STARTED_YET;
        textViewStopWatchHours.setText("00:");
        textViewStopWatchMinutes.setText("00:");
        textViewStopWatchSeconds.setText("00:");
        textViewStopWatchTenSeconds.setText("00");
        stopWatchState = STATE_INITIAL;
    }

    private void configStartState() {
        buttonResumeStopWatch.setVisibility(View.GONE);
        buttonStartStopWatch.setVisibility(View.GONE);
        buttonStopStopWatch.setVisibility(View.VISIBLE);
        buttonResetStopWatch.setVisibility(View.VISIBLE);
        stopWatchState = STATE_START;
    }

    private void configStopState() {
        buttonStartStopWatch.setVisibility(View.GONE);
        buttonStopStopWatch.setVisibility(View.GONE);
        buttonResumeStopWatch.setVisibility(View.VISIBLE);
        buttonResetStopWatch.setVisibility(View.VISIBLE);
        stopWatchState = STATE_STOP;
        updateTimerUI(tenMilliSecondsRemaining);
    }

    protected void updateTimerUI(long remainingTime) {
        long mSeconds = StopwatchLogic.calculateRemainingTime(StopwatchLogic.LONG_DURATION_FOR_TIMER, remainingTime);
        String formattedTime = StopwatchLogic.formatTime(mSeconds);
        String[] timeParts = formattedTime.split(":");

        textViewStopWatchHours.setText(timeParts[0] + ":");
        textViewStopWatchMinutes.setText(timeParts[1] + ":");
        textViewStopWatchSeconds.setText(timeParts[2] + ":");
        textViewStopWatchTenSeconds.setText(timeParts[3]);

        tenMilliSecondsRemaining = remainingTime;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_start_stop_watch) {
            countDownTimerCombine(tenMilliSecondsRemaining);
            configStartState();
        } else if (view.getId() == R.id.button_stop_stop_watch) {
            countDownTimer.cancel();
            configStopState();
        } else if (view.getId() == R.id.button_reset_stop_watch) {
            countDownTimer.cancel();
            configInitialState();
        } else if (view.getId() == R.id.button_resume_stop_watch) {
            countDownTimerCombine(tenMilliSecondsRemaining);
            configStartState();
        }
    }
}
