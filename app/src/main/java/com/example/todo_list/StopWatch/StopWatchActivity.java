package com.example.todo_list.StopWatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

/**
 * Activity class for the stopwatch functionality.
 */
public class StopWatchActivity extends AppCompatActivity implements View.OnClickListener {

    // Constants representing different states of the stopwatch
    private static final byte STATE_INITIAL = 0;
    private static final byte STATE_START = 1;
    private static final byte STATE_STOP = 2;
    private static final byte STATE_FINISHED = 3;

    // Constant representing timer has not started yet
    private static final long TIMER_HAS_NOT_STARTED_YET = -1;
    // Long duration for the timer
    private static final long LONG_DURATION_FOR_TIMER = 3_660_099;

    // Variables to manage stopwatch state and time
    private long tenMilliSecondsRemaining = TIMER_HAS_NOT_STARTED_YET;
    private byte stopWatchState = STATE_INITIAL;

    // UI elements
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

    // Stopwatch logic instance
    private final StopwatchLogic stopwatchLogic;

    /**
     * Default constructor. Initializes the StopwatchLogic instance using the factory.
     */
    public StopWatchActivity() {
        LogicFactory factory = new ConcreteLogicFactory();
        this.stopwatchLogic = factory.createStopwatchLogic();
    }

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

    /**
     * Configures the activity by setting up UI elements and initial state.
     */
    private void configure() {
        findViewByIds();
        setOnClickListeners();
        goToSuitableState();
    }

    /**
     * Initializes UI elements.
     */
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

    /**
     * Sets click listeners for buttons.
     */
    private void setOnClickListeners() {
        buttonStartStopWatch.setOnClickListener(this);
        buttonStopStopWatch.setOnClickListener(this);
        buttonResumeStopWatch.setOnClickListener(this);
        buttonResetStopWatch.setOnClickListener(this);
    }

    /**
     * Determines the suitable state of the stopwatch and configures UI accordingly.
     */
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

    /**
     * Combines the count down timer.
     *
     * @param remainingSecondsStatus The remaining seconds of the stopwatch.
     */
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

    /**
     * Configures UI for the initial state of the stopwatch.
     */
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

    /**
     * Configures UI for the start state of the stopwatch.
     */
    private void configStartState() {
        buttonResumeStopWatch.setVisibility(View.GONE);
        buttonStartStopWatch.setVisibility(View.GONE);
        buttonStopStopWatch.setVisibility(View.VISIBLE);
        buttonResetStopWatch.setVisibility(View.VISIBLE);
        stopWatchState = STATE_START;
    }

    /**
     * Configures UI for the stop state of the stopwatch.
     */
    private void configStopState() {
        buttonStartStopWatch.setVisibility(View.GONE);
        buttonStopStopWatch.setVisibility(View.GONE);
        buttonResumeStopWatch.setVisibility(View.VISIBLE);
        buttonResetStopWatch.setVisibility(View.VISIBLE);
        stopWatchState = STATE_STOP;
        updateTimerUI(tenMilliSecondsRemaining);
    }

    /**
     * Updates the UI with the remaining time of the stopwatch.
     *
     * @param remainingTime The remaining time of the stopwatch in milliseconds.
     */
    protected void updateTimerUI(long remainingTime) {
        long mSeconds = stopwatchLogic.calculateRemainingTime(StopwatchLogic.LONG_DURATION_FOR_TIMER, remainingTime);
        String formattedTime = stopwatchLogic.formatTime(mSeconds);
        String[] timeParts = formattedTime.split(":");

        textViewStopWatchHours.setText(timeParts[0] + ":");
        textViewStopWatchMinutes.setText(timeParts[1] + ":");
        textViewStopWatchSeconds.setText(timeParts[2] + ":");
        textViewStopWatchTenSeconds.setText(timeParts[3]);

        tenMilliSecondsRemaining = remainingTime;
    }

    /**
     * Handles click events for buttons.
     *
     * @param view The view that was clicked.
     */
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

    // Helper method for testing purposes
    public String getDisplayedTime() {
        return textViewStopWatchHours.getText().toString() +
                textViewStopWatchMinutes.getText().toString() +
                textViewStopWatchSeconds.getText().toString() +
                textViewStopWatchTenSeconds.getText().toString();
    }

    // Helper method for testing purposes
    public void setDurationForTesting(long duration) {
        tenMilliSecondsRemaining = duration;
    }
}
