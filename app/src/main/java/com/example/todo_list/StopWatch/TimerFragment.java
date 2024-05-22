package com.example.todo_list.StopWatch;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todo_list.R;

/**
 * Fragment that implements the timer functionality.
 * It includes start, stop, resume, and reset functionalities for the timer.
 * This fragment is designed to be used within an activity to manage timer operations.
 */
public class TimerFragment extends Fragment implements View.OnClickListener {

    // Constants representing the different states of the timer
    /**
     * Initial state of the timer, when it is first created and not yet started.
     */
    protected static final int STATE_INITIAL = 0;

    /**
     * State of the timer when it is running.
     */
    protected static final int STATE_START = 1;

    /**
     * State of the timer when it is stopped.
     */
    protected static final int STATE_STOP = 2;

    /**
     * State of the timer when it has finished counting down.
     */
    private static final int STATE_FINISHED = 3;

    // The duration of the timer in milliseconds
    /**
     * The duration of the timer in milliseconds.
     */
    long timerDuration = 0;

    // The current state of the timer
    /**
     * The current state of the timer.
     */
    protected int timerState = STATE_INITIAL;

    // Fragment lifecycle states
    private static final int FRAGMENT_STATE_ON_RESUME = 1;
    private static final int FRAGMENT_STATE_ON_PAUSE = 2;
    private int fragmentState;

    // UI components
    private Button buttonStartTimer;
    private Button buttonStopTimer;
    private Button buttonResumeTimer;
    private Button buttonResetTimer;

    private TextView textViewTimerSeconds;
    private TextView textViewTimerMinutes;
    private TextView textViewTimerHours;

    private CountDownTimer countDownTimer;

    NumberPicker numberPickerSeconds;
    NumberPicker numberPickerMinutes;
    NumberPicker numberPickerHours;

    private LinearLayout linearLayoutTimerContainer;
    private LinearLayout linearLayoutTimerInputNumberContainer;
    private LinearLayout linearLayoutTimerMessageContainer;
    private static TimerFragment instance;

    /**
     * Gets the singleton instance of the TimerFragment.
     *
     * @return The singleton instance.
     */
    public static TimerFragment getInstance() {
        if (instance == null) {
            instance = new TimerFragment();
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
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configure();
    }

    /**
     * Handles button click events to start, stop, resume, or reset the timer.
     * This method implements the logic for what should happen when each button is clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_start_timer) {
            if (numberPickerSeconds.getValue() == 0 && numberPickerMinutes.getValue() == 0 && numberPickerHours.getValue() == 0) {
                Toast.makeText(getContext(), getString(R.string.wrongInputForTimer), Toast.LENGTH_SHORT).show();
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
     * Called when the fragment is paused.
     * This method updates the fragment's state to paused.
     */
    @Override
    public void onPause() {
        super.onPause();
        fragmentState = FRAGMENT_STATE_ON_PAUSE;
    }

    /**
     * Called when the fragment is resumed.
     * This method updates the fragment's state to resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        fragmentState = FRAGMENT_STATE_ON_RESUME;
    }

    /**
     * Calculates the input time from the number pickers and updates the timer duration.
     */
    private void calculateInputTime() {
        long seconds = numberPickerSeconds.getValue();
        long minutes = numberPickerMinutes.getValue();
        long hours = numberPickerHours.getValue();
        timerDuration = TimerLogic.calculateTimerDuration(hours, minutes, seconds);
    }

    /**
     * Configures the fragment by finding views, setting listeners, and setting the initial state.
     * This method is called after the fragment's view has been created to initialize the components.
     */
    private void configure() {
        findViewByIds();
        setOnClickListeners();
        setInputNumbersValues();
        goToSuitableState();
    }

    /**
     * Finds the views by their IDs and assigns them to the corresponding member variables.
     * This method is used to link the UI components defined in the XML layout with the Java code.
     */
    private void findViewByIds() {
        View view = getView();
        linearLayoutTimerContainer = view.findViewById(R.id.linearLayout_timer_container);
        linearLayoutTimerInputNumberContainer = view.findViewById(R.id.linearLayout_timer_input_number_container);
        linearLayoutTimerMessageContainer = view.findViewById(R.id.linearLayout_timer_message_container);

        numberPickerSeconds = view.findViewById(R.id.number_picker_seconds);
        numberPickerMinutes = view.findViewById(R.id.number_picker_minutes);
        numberPickerHours = view.findViewById(R.id.number_picker_hours);

        textViewTimerSeconds = view.findViewById(R.id.textView_timer_s);
        textViewTimerMinutes = view.findViewById(R.id.textView_timer_m);
        textViewTimerHours = view.findViewById(R.id.textView_timer_h);

        buttonStartTimer = view.findViewById(R.id.button_start_timer);
        buttonStopTimer = view.findViewById(R.id.button_stop_timer);
        buttonResumeTimer = view.findViewById(R.id.button_resume_timer);
        buttonResetTimer = view.findViewById(R.id.button_reset_timer);
    }

    /**
     * Sets the click listeners for the buttons.
     * This method assigns the fragment as the click listener for the start, stop, resume, and reset buttons.
     */
    private void setOnClickListeners() {
        buttonStartTimer.setOnClickListener(this);
        buttonStopTimer.setOnClickListener(this);
        buttonResumeTimer.setOnClickListener(this);
        buttonResetTimer.setOnClickListener(this);
    }

    /**
     * Sets the values for the number pickers.
     * This method initializes the number pickers with values from 0 to their respective maximum values.
     */
    private void setInputNumbersValues() {
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
     * Sets the initial state of the fragment based on the current state of the timer.
     * This method determines which UI elements should be visible or hidden depending on the timer's state.
     */
    private void goToSuitableState() {
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
     * Starts or resumes the timer.
     * This method initializes and starts the countdown timer based on the remaining duration.
     */
    private void startOrResumeTimer() {
        if (timerDuration == 0) return;

        countDownTimer = new CountDownTimer(timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerDuration = millisUntilFinished;
                if (fragmentState == FRAGMENT_STATE_ON_RESUME)
                    updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerState = STATE_FINISHED;
                if (fragmentState == FRAGMENT_STATE_ON_RESUME)
                    configFinishState();
            }
        };
        countDownTimer.start();
    }

    /**
     * Updates the timer display with the remaining time.
     * This method updates the text views to display the remaining time in the format HH:mm:ss.
     *
     * @param remainingTime The remaining time in milliseconds.
     */
    private void updateTimerDisplay(long remainingTime) {
        String[] timeParts = TimerLogic.formatTime(remainingTime);

        textViewTimerHours.setText(timeParts[0]);
        textViewTimerMinutes.setText(timeParts[1]);
        textViewTimerSeconds.setText(timeParts[2]);

        int color = TimerLogic.getColorForTime(remainingTime, 11000, getResources().getColor(R.color.DarkRed), getResources().getColor(R.color.DarkGray));
        textViewTimerHours.setTextColor(color);
        textViewTimerMinutes.setTextColor(color);
        textViewTimerSeconds.setTextColor(color);
    }

    /**
     * Configures the initial state of the timer.
     * This method sets the visibility of buttons and initializes the number pickers and text views to display the default time.
     */
    private void configInitialState() {
        linearLayoutTimerMessageContainer.setVisibility(View.GONE);
        buttonStartTimer.setVisibility(View.VISIBLE);
        buttonStopTimer.setVisibility(View.GONE);
        buttonResumeTimer.setVisibility(View.GONE);
        buttonResetTimer.setVisibility(View.GONE);
        linearLayoutTimerInputNumberContainer.setVisibility(View.VISIBLE);
        linearLayoutTimerContainer.setVisibility(View.GONE);
        timerState = STATE_INITIAL;
        timerDuration = 0;
        numberPickerSeconds.setValue(0);
        numberPickerMinutes.setValue(0);
        numberPickerHours.setValue(0);
    }

    /**
     * Configures the start state of the timer.
     * This method sets the visibility of buttons to reflect that the timer is running.
     */
    private void configStartState() {
        if (linearLayoutTimerInputNumberContainer.getVisibility() == View.VISIBLE) {
            linearLayoutTimerInputNumberContainer.setVisibility(View.GONE);
            linearLayoutTimerMessageContainer.setVisibility(View.GONE);
            linearLayoutTimerContainer.setVisibility(View.VISIBLE);
        }
        buttonResumeTimer.setVisibility(View.GONE);
        buttonStartTimer.setVisibility(View.GONE);
        buttonStopTimer.setVisibility(View.VISIBLE);
        buttonResetTimer.setVisibility(View.VISIBLE);
        timerState = STATE_START;
        updateTimerDisplay(timerDuration);
    }

    /**
     * Configures the stop state of the timer.
     * This method sets the visibility of buttons to reflect that the timer is stopped.
     */
    private void configStopState() {
        buttonStopTimer.setVisibility(View.GONE);
        buttonStartTimer.setVisibility(View.GONE);
        linearLayoutTimerInputNumberContainer.setVisibility(View.GONE);
        linearLayoutTimerMessageContainer.setVisibility(View.GONE);
        buttonResumeTimer.setVisibility(View.VISIBLE);
        buttonResetTimer.setVisibility(View.VISIBLE);
        timerState = STATE_STOP;
        updateTimerDisplay(timerDuration);
    }

    /**
     * Configures the finish state of the timer.
     * This method sets the visibility of buttons and updates the text views to reflect that the timer has finished.
     */
    private void configFinishState() {
        linearLayoutTimerInputNumberContainer.setVisibility(View.GONE);
        linearLayoutTimerContainer.setVisibility(View.VISIBLE);
        buttonResumeTimer.setVisibility(View.GONE);
        buttonStartTimer.setVisibility(View.GONE);
        buttonStopTimer.setVisibility(View.GONE);
        buttonResetTimer.setVisibility(View.VISIBLE);
        textViewTimerSeconds.setText(String.format("%02d", 0));
        textViewTimerMinutes.setText(String.format("%02d:", 0));
        textViewTimerHours.setText(String.format("%02d:", 0));
        linearLayoutTimerMessageContainer.setVisibility(View.VISIBLE);
        makeTimerRed();
    }

    /**
     * Sets the timer text color to red.
     * This method updates the text color of the hours, minutes, and seconds text views to red.
     */
    private void makeTimerRed() {
        textViewTimerSeconds.setTextColor(getResources().getColor(R.color.DarkRed));
        textViewTimerMinutes.setTextColor(getResources().getColor(R.color.DarkRed));
        textViewTimerHours.setTextColor(getResources().getColor(R.color.DarkRed));
    }

    /**
     * Sets the timer text color to gray.
     * This method updates the text color of the hours, minutes, and seconds text views to gray.
     */
    private void makeTimerGray() {
        textViewTimerSeconds.setTextColor(getResources().getColor(R.color.DarkGray));
        textViewTimerMinutes.setTextColor(getResources().getColor(R.color.DarkGray));
        textViewTimerHours.setTextColor(getResources().getColor(R.color.DarkGray));
    }
}
