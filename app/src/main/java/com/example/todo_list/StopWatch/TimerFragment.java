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

public class TimerFragment extends Fragment implements View.OnClickListener {
    protected static final int STATE_INITIAL = 0;
    protected static final int STATE_START = 1;
    protected static final int STATE_STOP = 2;
    private static final int STATE_FINISHED = 3;

    long timerDuration = 0;
    protected int timerState = STATE_INITIAL;

    private static final int FRAGMENT_STATE_ON_RESUME = 1;
    private static final int FRAGMENT_STATE_ON_PAUSE = 2;
    private int fragmentState;

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

    public static TimerFragment getInstance() {
        if (instance == null) {
            instance = new TimerFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configure();
    }

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

    @Override
    public void onPause() {
        super.onPause();
        fragmentState = FRAGMENT_STATE_ON_PAUSE;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentState = FRAGMENT_STATE_ON_RESUME;
    }

    private void calculateInputTime() {
        long seconds = numberPickerSeconds.getValue();
        long minutes = numberPickerMinutes.getValue();
        long hours = numberPickerHours.getValue();
        timerDuration = TimerLogic.calculateTimerDuration(hours, minutes, seconds);
    }

    private void configure() {
        findViewByIds();
        setOnClickListeners();
        setInputNumbersValues();
        goToSuitableState();
    }

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

    private void setOnClickListeners() {
        buttonStartTimer.setOnClickListener(this);
        buttonStopTimer.setOnClickListener(this);
        buttonResumeTimer.setOnClickListener(this);
        buttonResetTimer.setOnClickListener(this);
    }

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

    private void makeTimerRed() {
        textViewTimerSeconds.setTextColor(getResources().getColor(R.color.DarkRed));
        textViewTimerMinutes.setTextColor(getResources().getColor(R.color.DarkRed));
        textViewTimerHours.setTextColor(getResources().getColor(R.color.DarkRed));
    }

    private void makeTimerGray() {
        textViewTimerSeconds.setTextColor(getResources().getColor(R.color.DarkGray));
        textViewTimerMinutes.setTextColor(getResources().getColor(R.color.DarkGray));
        textViewTimerHours.setTextColor(getResources().getColor(R.color.DarkGray));
    }
}
