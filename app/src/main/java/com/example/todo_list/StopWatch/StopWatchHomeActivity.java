package com.example.todo_list.StopWatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.todo_list.App_Options.FragmentFactory;
import com.example.todo_list.R;

/**
 * Activity that serves as the home for the StopWatch and Timer fragments.
 * This activity allows users to switch between a stopwatch and a timer
 * through buttons that replace the current fragment displayed in the UI.
 */
public class StopWatchHomeActivity extends AppCompatActivity {
    // UI components
    private Button buttonOpenStopWatchFragment;
    private Button buttonOpenTimerFragment;

    // Constants for fragment status
    private static final byte STATUS_STOP_WATCH = 1;
    private static final byte STATUS_TIMER = 2;

    // Current fragment status
    private byte fragmentStatus;

    // Fragments for stopwatch and timer
    private StopwatchFragment stopwatchFragment;
    private TimerFragment timerFragment;

    /**
     * Called when the activity is first created.
     * This method sets up the user interface and initializes the fragments.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}. <b>Note: Otherwise it is null.</b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch_home);
        configure();
    }

    /**
     * Configures the activity by finding views, setting listeners, and initializing fragments.
     * This method is responsible for the initial setup required for the activity.
     */
    private void configure() {
        findViewsById();
        setOnClickListeners();
        initializing();
    }

    /**
     * Finds the views by their IDs and assigns them to the corresponding member variables.
     * This method links the UI components defined in the XML layout with the Java code.
     */
    private void findViewsById() {
        buttonOpenStopWatchFragment = findViewById(R.id.button_open_stop_watch);
        buttonOpenTimerFragment = findViewById(R.id.button_open_timer);
    }

    /**
     * Sets the click listeners for the buttons.
     * Each button, when clicked, will replace the current fragment displayed in the container with either the stopwatch or timer fragment.
     */
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

    /**
     * Initializes the fragments and sets the default fragment to StopwatchFragment.
     * This method uses the FragmentFactory to create instances of the fragments and displays the stopwatch fragment by default.
     */
    private void initializing() {
        stopwatchFragment = (StopwatchFragment) FragmentFactory.createFragment(FragmentFactory.STATUS_STOP_WATCH);
        timerFragment = (TimerFragment) FragmentFactory.createFragment(FragmentFactory.STATUS_TIMER);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container2, stopwatchFragment).commit();
        fragmentStatus = STATUS_STOP_WATCH;
    }
}
