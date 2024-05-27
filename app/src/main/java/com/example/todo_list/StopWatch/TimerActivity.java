package com.example.todo_list.StopWatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

/**
 * Activity for the timer functionality.
 * Allows users to set a countdown timer and start, pause, resume, or reset it.
 */
public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    // Constants representing different states of the timer
    private static final int STATE_INITIAL = 0;
    private static final int STATE_START = 1;
    private static final int STATE_STOP = 2;
    private static final int STATE_FINISHED = 3;

    // Instance variables
    private int timerState = STATE_INITIAL;
    private long timerDuration = 0;
    private CountDownTimer countDownTimer;

    // UI elements
    private Button buttonStartTimer;
    private Button buttonStopTimer;
    private Button buttonResumeTimer;
    private Button buttonResetTimer;
    private TextView textViewTimerHours;
    private TextView textViewTimerMinutes;
    private TextView textViewTimerSeconds;
    private NumberPicker numberPickerSeconds;
    private NumberPicker numberPickerMinutes;
    private NumberPicker numberPickerHours;
    private LinearLayout timerContainer;
    private LinearLayout timerInputContainer;
    private LinearLayout timerMessageContainer;
    private Button buttonOpenStopWatch;
    private Button buttonOpenTimer;

    // Get instance of TimerLogic using the factory
    private final TimerLogic timerLogic;

    /**
     * Constructor for TimerActivity.
     * Initializes TimerLogic using a concrete factory.
     */
    public TimerActivity() {
        LogicFactory factory = new ConcreteLogicFactory();
        this.timerLogic = factory.createTimerLogic();
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
        setContentView(R.layout.activity_timer);
        configure();

        buttonOpenStopWatch = findViewById(R.id.button_open_stop_watch);
        buttonOpenTimer = findViewById(R.id.button_open_timer);

        buttonOpenStopWatch.setOnClickListener(v -> {
            startActivity(new Intent(TimerActivity.this, StopWatchActivity.class));
        });

        buttonOpenTimer.setOnClickListener(v -> {
            // Do nothing as we are already in TimerActivity
        });
    }

    /**
     * Configures the initial state of the activity.
     */
    private void configure() {
        findViewByIds();
        setOnClickListeners();
        setInputNumbersValues();
        goToSuitableState();
    }

    /**
     * Finds and assigns UI elements to their respective variables.
     */
    private void findViewByIds() {
        // Find and assign UI elements
        timerContainer = findViewById(R.id.linearLayout_timer_container);
        timerInputContainer = findViewById(R.id.linearLayout_timer_input_number_container);
        timerMessageContainer = findViewById(R.id.linearLayout_timer_message_container);
        numberPickerSeconds = findViewById(R.id.number_picker_seconds);
        numberPickerMinutes = findViewById(R.id.number_picker_minutes);
        numberPickerHours = findViewById(R.id.number_picker_hours);
        textViewTimerSeconds = findViewById(R.id.textView_timer_s);
        textViewTimerMinutes = findViewById(R.id.textView_timer_m);
        textViewTimerHours = findViewById(R.id.textView_timer_h);
        buttonStartTimer = findViewById(R.id.button_start_timer);
        buttonStopTimer = findViewById(R.id.button_stop_timer);
        buttonResumeTimer = findViewById(R.id.button_resume_timer);
        buttonResetTimer = findViewById(R.id.button_reset_timer);
    }

    /**
     * Sets click listeners for buttons.
     */
    private void setOnClickListeners() {
        buttonStartTimer.setOnClickListener(this);
        buttonStopTimer.setOnClickListener(this);
        buttonResumeTimer.setOnClickListener(this);
        buttonResetTimer.setOnClickListener(this);
    }

    /**
     * Sets values and configurations for number pickers.
     */
    private void setInputNumbersValues() {
        // Set values and configurations for number pickers
        final String[] secondsNumbers = new String[61];
        for (int i = 0; i < secondsNumbers.length; i++)
            secondsNumbers[i] = String.format("%02d", i);

        String[] hoursNumbers = new String[100];
        for (int i = 0; i < hoursNumbers.length; i++)
            hoursNumbers[i] = String.format("%02d", i);

        numberPickerSeconds.setMaxValue(60);
        numberPickerSeconds.setMinValue(0);
        numberPickerSeconds.setDisplayedValues(secondsNumbers);

        numberPickerMinutes.setMaxValue(60);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setDisplayedValues(secondsNumbers);

        numberPickerHours.setMaxValue(99);
        numberPickerHours.setMinValue(0);
        numberPickerHours.setDisplayedValues(hoursNumbers);
    }

    /**
     * Determines the suitable state of the activity based on the timer state and updates UI accordingly.
     */
    private void goToSuitableState() {
        // Determine suitable state and update UI
        if (timerState == STATE_INITIAL) {
            configInitialState();
        } else if (timerState == STATE_START) {
            configStartState();
        } else if (timerState == STATE_STOP) {
            configStopState();
        } else if (timerState == STATE_FINISHED) {
            configFinishState();
        }
    }

    /**
     * Handles button click events.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        // Handle button click events
        if (view.getId() == R.id.button_start_timer) {
            if (numberPickerSeconds.getValue() == 0 && numberPickerMinutes.getValue() == 0 && numberPickerHours.getValue() == 0) {
                Toast.makeText(this, getString(R.string.wrongInputForTimer), Toast.LENGTH_SHORT).show();
            } else {
                configStartState();
                calculateInputTime();
                startOrResumeTimer();
            }
        } else if (view.getId() == R.id.button_stop_timer) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            configStopState();
        } else if (view.getId() == R.id.button_reset_timer) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            configInitialState();
        } else if (view.getId() == R.id.button_resume_timer) {
            startOrResumeTimer();
            configStartState();
        }
    }

    /**
     * Calculates the input time set by the user.
     */
    private void calculateInputTime() {
        long seconds = numberPickerSeconds.getValue();
        long minutes = numberPickerMinutes.getValue();
        long hours = numberPickerHours.getValue();
        timerDuration = timerLogic.calculateTimerDuration(hours, minutes, seconds);
    }

    /**
     * Starts or resumes the timer.
     */
    private void startOrResumeTimer() {
        // Starts or resumes the timer
        if (timerDuration == 0) return;

        countDownTimer = new CountDownTimer(timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerDuration = millisUntilFinished;
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerState = STATE_FINISHED;
                configFinishState();
            }
        };
        countDownTimer.start();
    }

    /**
     * Updates the timer display with the remaining time.
     *
     * @param remainingTime The remaining time of the timer.
     */
    private void updateTimerDisplay(long remainingTime) {
        // Updates the timer display with the remaining time
        String[] timeParts = timerLogic.formatTime(remainingTime);
        textViewTimerHours.setText(timeParts[0]);
        textViewTimerMinutes.setText(timeParts[1]);
        textViewTimerSeconds.setText(timeParts[2]);

        int color = timerLogic.getColorForTime(remainingTime, 11000, getResources().getColor(R.color.DarkRed), getResources().getColor(R.color.DarkGray));
        textViewTimerHours.setTextColor(color);
        textViewTimerMinutes.setTextColor(color);
        textViewTimerSeconds.setTextColor(color);
    }

    /**
     * Configures the initial state of the activity.
     */
    private void configInitialState() {
        // Configures the initial state of the activity
        timerMessageContainer.setVisibility(View.GONE);
        buttonStartTimer.setVisibility(View.VISIBLE);
        buttonStopTimer.setVisibility(View.GONE);
        buttonResumeTimer.setVisibility(View.GONE);
        buttonResetTimer.setVisibility(View.GONE);
        timerInputContainer.setVisibility(View.VISIBLE);
        timerContainer.setVisibility(View.GONE);
        timerState = STATE_INITIAL;
        timerDuration = 0;
        numberPickerSeconds.setValue(0);
        numberPickerMinutes.setValue(0);
        numberPickerHours.setValue(0);
    }

    /**
     * Configures the state of the activity when the timer starts.
     */
    private void configStartState() {
        // Configures the state of the activity when the timer starts
        if (timerInputContainer.getVisibility() == View.VISIBLE) {
            timerInputContainer.setVisibility(View.GONE);
            timerMessageContainer.setVisibility(View.GONE);
            timerContainer.setVisibility(View.VISIBLE);
        }
        buttonResumeTimer.setVisibility(View.GONE);
        buttonStartTimer.setVisibility(View.GONE);
        buttonStopTimer.setVisibility(View.VISIBLE);
        buttonResetTimer.setVisibility(View.VISIBLE);
        timerState = STATE_START;
        updateTimerDisplay(timerDuration);
    }

    /**
     * Configures the state of the activity when the timer is stopped.
     */
    private void configStopState() {
        // Configures the state of the activity when the timer is stopped
        buttonStopTimer.setVisibility(View.GONE);
        buttonStartTimer.setVisibility(View.GONE);
        timerInputContainer.setVisibility(View.GONE);
        timerMessageContainer.setVisibility(View.GONE);
        buttonResumeTimer.setVisibility(View.VISIBLE);
        buttonResetTimer.setVisibility(View.VISIBLE);
        timerState = STATE_STOP;
        updateTimerDisplay(timerDuration);
    }

    /**
     * Configures the state of the activity when the timer finishes.
     */
    private void configFinishState() {
        // Configures the state of the activity when the timer finishes
        timerInputContainer.setVisibility(View.GONE);
        timerContainer.setVisibility(View.VISIBLE);
        buttonResumeTimer.setVisibility(View.GONE);
        buttonStartTimer.setVisibility(View.GONE);
        buttonStopTimer.setVisibility(View.GONE);
        buttonResetTimer.setVisibility(View.VISIBLE);
        textViewTimerSeconds.setText(String.format("%02d", 0));
        textViewTimerMinutes.setText(String.format("%02d:", 0));
        textViewTimerHours.setText(String.format("%02d:", 0));
        timerMessageContainer.setVisibility(View.VISIBLE);
        makeTimerRed();
    }

    /**
     * Changes the color of the timer display to red.
     */
    private void makeTimerRed() {
        // Changes the color of the timer display to red
        textViewTimerSeconds.setTextColor(getResources().getColor(R.color.DarkRed));
        textViewTimerMinutes.setTextColor(getResources().getColor(R.color.DarkRed));
        textViewTimerHours.setTextColor(getResources().getColor(R.color.DarkRed));
    }
}
