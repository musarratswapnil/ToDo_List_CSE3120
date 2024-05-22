package com.example.todo_list.StopWatch;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.todo_list.R;

/**
 * Fragment that implements the stopwatch functionality.
 * It includes start, stop, resume, and reset functionalities for the stopwatch.
 * This fragment is designed to be used within an activity to manage stopwatch operations.
 */
public class StopwatchFragment extends Fragment implements View.OnClickListener {

    
    /**
     * Initial state of the stopwatch, when it is first created and not yet started.
     */
    protected static final byte STATE_INITIAL = 0;

    /**
     * State of the stopwatch when it is running.
     */
    protected static final byte STATE_START = 1;

    /**
     * State of the stopwatch when it is stopped.
     */
    protected static final byte STATE_STOP = 2;

    /**
     * State of the stopwatch when it has finished counting down.
     */
    private static final byte STATE_FINISHED = 3;

    /**
     * Constant representing the state when the timer has not started yet.
     */
    protected final static long TIMER_HAS_NOT_STARTED_YET = -1;

    /**
     * Constant representing a long duration for the timer (59 minutes, 59 seconds, 99 milliseconds).
     */
    private final static long LONG_DURATION_FOR_TIMER = 3_660_099; // milliseconds equal to 59:59:99

    /**
     * The remaining time in ten milliseconds increments.
     */
    protected long tenMilliSecondsRemaining = TIMER_HAS_NOT_STARTED_YET;

    /**
     * The current state of the stopwatch.
     */
    protected byte stopWatchState = STATE_INITIAL;

    // UI components
    Button buttonStartStopWatch;
    Button buttonStopStopWatch;
    Button buttonResumeStopWatch;
    Button buttonResetStopWatch;

    TextView textViewStopWatchHours;
    TextView textViewStopWatchMinutes;
    TextView textViewStopWatchSeconds;
    TextView textViewStopWatchTenSeconds;

    // Countdown timer for the stopwatch
    /**
     * Countdown timer for the stopwatch.
     */
    CountDownTimer countDownTimer;

    // Singleton instance of the fragment
    private static StopwatchFragment instance;

    /**
     * Gets the singleton instance of the StopwatchFragment.
     *
     * @return The singleton instance.
     */
    public static StopwatchFragment getInstance() {
        if (instance == null) {
            instance = new StopwatchFragment();
        }
        return instance;
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false);
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configure();
    }

    /**
     * Configures the fragment by finding views, setting listeners, and setting the initial state.
     * This method is called after the fragment's view has been created to initialize the components.
     */
    private void configure() {
        findViewByIds();
        setOnClickListeners();
        goToSuitableState();
    }

    /**
     * Finds the views by their IDs and assigns them to the corresponding member variables.
     * This method is used to link the UI components defined in the XML layout with the Java code.
     */
    private void findViewByIds() {
        buttonStartStopWatch = getView().findViewById(R.id.button_start_stop_watch);
        buttonStopStopWatch = getView().findViewById(R.id.button_stop_stop_watch);
        buttonResumeStopWatch = getView().findViewById(R.id.button_resume_stop_watch);
        buttonResetStopWatch = getView().findViewById(R.id.button_reset_stop_watch);

        textViewStopWatchHours = getView().findViewById(R.id.textView_stopwatch_h);
        textViewStopWatchMinutes = getView().findViewById(R.id.textView_stopwatch_m);
        textViewStopWatchSeconds = getView().findViewById(R.id.textView_stopwatch_s);
        textViewStopWatchTenSeconds = getView().findViewById(R.id.textView_stopwatch_10ms);
    }

    /**
     * Sets the click listeners for the buttons.
     * This method assigns the fragment as the click listener for the start, stop, resume, and reset buttons.
     */
    private void setOnClickListeners() {
        buttonStartStopWatch.setOnClickListener(this);
        buttonStopStopWatch.setOnClickListener(this);
        buttonResumeStopWatch.setOnClickListener(this);
        buttonResetStopWatch.setOnClickListener(this);
    }

    /**
     * Sets the initial state of the fragment based on the current state of the stopwatch.
     * This method determines which UI elements should be visible or hidden depending on the stopwatch's state.
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
     * Combines the countdown timer with the remaining time.
     * This method initializes and starts the countdown timer based on the remaining time provided.
     *
     * @param remainingSecondsStatus The remaining time in milliseconds.
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
     * Configures the initial state of the stopwatch.
     * This method sets the visibility of buttons and initializes the text views to display the default time.
     */
    private void configInitialState() {
        buttonStartStopWatch.setVisibility(View.VISIBLE);
        buttonStopStopWatch.setVisibility(View.GONE);
        buttonResumeStopWatch.setVisibility(View.GONE);
        buttonResetStopWatch.setVisibility(View.GONE);
        tenMilliSecondsRemaining = StopwatchLogic.TIMER_HAS_NOT_STARTED_YET;
        textViewStopWatchHours.setText("00:");
        textViewStopWatchMinutes.setText("00:");
        textViewStopWatchSeconds.setText("00:");
        textViewStopWatchTenSeconds.setText("00");
        stopWatchState = STATE_INITIAL;
    }

    /**
     * Configures the start state of the stopwatch.
     * This method sets the visibility of buttons to reflect that the stopwatch is running.
     */
    private void configStartState() {
        buttonResumeStopWatch.setVisibility(View.GONE);
        buttonStartStopWatch.setVisibility(View.GONE);
        buttonStopStopWatch.setVisibility(View.VISIBLE);
        buttonResetStopWatch.setVisibility(View.VISIBLE);
        stopWatchState = STATE_START;
    }

    /**
     * Configures the stop state of the stopwatch.
     * This method sets the visibility of buttons to reflect that the stopwatch is stopped.
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
     * Updates the timer UI with the remaining time.
     * This method updates the text views to display the remaining time in the format HH:mm:ss:SS.
     *
     * @param remainingTime The remaining time in milliseconds.
     */
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

    /**
     * Handles button click events to start, stop, resume, or reset the stopwatch.
     * This method implements the logic for what should happen when each button is clicked.
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
}
