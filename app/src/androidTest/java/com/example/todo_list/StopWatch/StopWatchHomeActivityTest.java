package com.example.todo_list.StopWatch;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo_list.R;
import com.example.todo_list.StopWatch.StopWatchHomeActivity;
import com.example.todo_list.StopWatch.StopwatchFragment;
import com.example.todo_list.StopWatch.TimerFragment;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class StopWatchHomeActivityTest {

    @Test
    public void testOpenStopwatchFragment() {
        try (ActivityScenario<StopWatchHomeActivity> scenario = ActivityScenario.launch(StopWatchHomeActivity.class)) {
            // Perform click to open StopwatchFragment
            Espresso.onView(ViewMatchers.withId(R.id.button_open_stop_watch))
                    .perform(ViewActions.click());

            // Verify that StopwatchFragment is displayed
            scenario.onActivity(activity -> {
                Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container2);
                assertTrue(currentFragment instanceof StopwatchFragment);
            });
        }
    }

    @Test
    public void testOpenTimerFragment() {
        try (ActivityScenario<StopWatchHomeActivity> scenario = ActivityScenario.launch(StopWatchHomeActivity.class)) {
            // Perform click to open TimerFragment
            Espresso.onView(ViewMatchers.withId(R.id.button_open_timer))
                    .perform(ViewActions.click());

            // Verify that TimerFragment is displayed
            scenario.onActivity(activity -> {
                Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container2);
                assertTrue(currentFragment instanceof TimerFragment);
            });
        }
    }
}
