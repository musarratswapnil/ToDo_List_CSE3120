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

public class StopwatchFragment extends Fragment implements View.OnClickListener {
    private static final byte STATE_INITIAL = 0;
    private static final byte STATE_START = 1;
    private static final byte STATE_STOP = 2;
    private static final byte STATE_FINISHED = 3;

    protected final static long TIMER_HAS_NOT_STARTED_YET = -1;
    private final static long LONG_DURATION_FOR_TIMER = 3_660_099; // milli seconds equal to 59:59:99
    protected long tenMilliSecondsRemaining = TIMER_HAS_NOT_STARTED_YET;

    private byte stopWatchState = STATE_INITIAL;

    Button buttonStartStopWatch;
    Button buttonStopStopWatch;
    Button buttonResumeStopWatch;
    Button buttonResetStopWatch;

    TextView textViewStopWatchHours;
    TextView textViewStopWatchMinutes;
    TextView textViewStopWatchSeconds;
    TextView textViewStopWatchTenSeconds;

    CountDownTimer countDownTimer;

    private static StopwatchFragment instance;

    public static StopwatchFragment getInstance() {
        if (instance == null) {
            instance = new StopwatchFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configure();
    }

    private void configure() {
        findViewByIds();
        setOnClickListeners();
        goToSuitableState();
    }

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
                break;
        }
    }

    protected void countDownTimerCombine(long remainingSecondsStatus) {
        long duration = LONG_DURATION_FOR_TIMER;
        if (remainingSecondsStatus != TIMER_HAS_NOT_STARTED_YET)
            duration = remainingSecondsStatus;

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
        tenMilliSecondsRemaining = StopwatchLogic.TIMER_HAS_NOT_STARTED_YET;
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
