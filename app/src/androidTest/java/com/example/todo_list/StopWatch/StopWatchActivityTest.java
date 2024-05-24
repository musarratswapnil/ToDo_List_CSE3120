package com.example.todo_list.StopWatch;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todo_list.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StopWatchActivityTest {

    @Before
    public void setUp() {
        // Launch the activity
        ActivityScenario.launch(StopWatchActivity.class);
    }

    @Test
    public void testStopWatchActivityInitialDisplay() {
        // Verify initial state
        Espresso.onView(withId(R.id.button_start_stop_watch)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_reset_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.textView_stopwatch_h)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.textView_stopwatch_m)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.textView_stopwatch_s)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.textView_stopwatch_10ms)).check(matches(isDisplayed()));
    }

    @Test
    public void testStopWatchStart() {
        // Click start button
        Espresso.onView(withId(R.id.button_start_stop_watch)).perform(ViewActions.click());

        // Verify the stopwatch is started
        Espresso.onView(withId(R.id.button_stop_stop_watch)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_start_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testStopWatchStop() {
        // Start the stopwatch first
        Espresso.onView(withId(R.id.button_start_stop_watch)).perform(ViewActions.click());

        // Click stop button
        Espresso.onView(withId(R.id.button_stop_stop_watch)).perform(ViewActions.click());

        // Verify the stopwatch is stopped
        Espresso.onView(withId(R.id.button_resume_stop_watch)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_start_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testStopWatchResume() {
        // Start and stop the stopwatch first
        Espresso.onView(withId(R.id.button_start_stop_watch)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.button_stop_stop_watch)).perform(ViewActions.click());

        // Click resume button
        Espresso.onView(withId(R.id.button_resume_stop_watch)).perform(ViewActions.click());

        // Verify the stopwatch is resumed
        Espresso.onView(withId(R.id.button_stop_stop_watch)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_start_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testStopWatchReset() {
        // Start the stopwatch first
        Espresso.onView(withId(R.id.button_start_stop_watch)).perform(ViewActions.click());

        // Click reset button
        Espresso.onView(withId(R.id.button_reset_stop_watch)).perform(ViewActions.click());

        // Verify the stopwatch is reset
        Espresso.onView(withId(R.id.button_start_stop_watch)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_reset_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.textView_stopwatch_h)).check(matches(withText("00:")));
        Espresso.onView(withId(R.id.textView_stopwatch_m)).check(matches(withText("00:")));
        Espresso.onView(withId(R.id.textView_stopwatch_s)).check(matches(withText("00:")));
        Espresso.onView(withId(R.id.textView_stopwatch_10ms)).check(matches(withText("00")));
    }
}
