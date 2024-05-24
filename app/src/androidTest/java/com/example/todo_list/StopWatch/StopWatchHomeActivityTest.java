package com.example.todo_list.StopWatch;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo_list.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class StopWatchHomeActivityTest {

    @Rule
    public ActivityScenarioRule<StopWatchHomeActivity> activityScenarioRule =
            new ActivityScenarioRule<>(StopWatchHomeActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testButtonsDisplayed() {
        // Check if both buttons are displayed
        onView(withId(R.id.button_open_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_open_timer)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigateToStopWatchActivity() {
        // Perform click on StopWatch button
        onView(withId(R.id.button_open_stop_watch)).perform(click());

        // Verify the intent to launch StopWatchActivity
        Intents.intended(IntentMatchers.hasComponent(StopWatchActivity.class.getName()));
    }

    @Test
    public void testNavigateToTimerActivity() {
        // Perform click on Timer button
        onView(withId(R.id.button_open_timer)).perform(click());

        // Verify the intent to launch TimerActivity
        Intents.intended(IntentMatchers.hasComponent(TimerActivity.class.getName()));
    }
}
