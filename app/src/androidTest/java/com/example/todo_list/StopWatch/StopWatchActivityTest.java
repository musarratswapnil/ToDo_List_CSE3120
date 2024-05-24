package com.example.todo_list.StopWatch;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo_list.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StopWatchActivityTest {

    @Rule
    public ActivityScenarioRule<StopWatchActivity> activityScenarioRule =
            new ActivityScenarioRule<>(StopWatchActivity.class);

    @Before
    public void setUp() {
        // Set up any necessary initial state or configurations here
    }

    @Test
    public void testInitialUIState() {
        // Verify initial state of the UI elements
        onView(withId(R.id.button_start_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_reset_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.textView_stopwatch_h)).check(matches(withText("00:")));
        onView(withId(R.id.textView_stopwatch_m)).check(matches(withText("00:")));
        onView(withId(R.id.textView_stopwatch_s)).check(matches(withText("00:")));
        onView(withId(R.id.textView_stopwatch_10ms)).check(matches(withText("00")));
    }

    @Test
    public void testStartStopwatch() {
        // Click on the start button and verify the state changes
        onView(withId(R.id.button_start_stop_watch)).perform(click());

        onView(withId(R.id.button_stop_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_start_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
    }

    @Test
    public void testStopStopwatch() {
        // Start the stopwatch, then stop it, and verify the state changes
        onView(withId(R.id.button_start_stop_watch)).perform(click());
        onView(withId(R.id.button_stop_stop_watch)).perform(click());

        onView(withId(R.id.button_resume_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
    }

    @Test
    public void testResetStopwatch() {
        // Start the stopwatch, stop it, then reset it, and verify the state changes
        onView(withId(R.id.button_start_stop_watch)).perform(click());
        onView(withId(R.id.button_stop_stop_watch)).perform(click());
        onView(withId(R.id.button_reset_stop_watch)).perform(click());

        onView(withId(R.id.button_start_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_reset_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.textView_stopwatch_h)).check(matches(withText("00:")));
        onView(withId(R.id.textView_stopwatch_m)).check(matches(withText("00:")));
        onView(withId(R.id.textView_stopwatch_s)).check(matches(withText("00:")));
        onView(withId(R.id.textView_stopwatch_10ms)).check(matches(withText("00")));
    }

    @Test
    public void testResumeStopwatch() {
        // Start the stopwatch, stop it, then resume it, and verify the state changes
        onView(withId(R.id.button_start_stop_watch)).perform(click());
        onView(withId(R.id.button_stop_stop_watch)).perform(click());
        onView(withId(R.id.button_resume_stop_watch)).perform(click());

        onView(withId(R.id.button_stop_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_start_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
    }
}
