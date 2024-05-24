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

@RunWith(AndroidJUnit4.class)
public class TimerActivityTest {

    @Before
    public void setUp() {
        // Launch the activity
        ActivityScenario.launch(TimerActivity.class);
    }

    @Test
    public void testTimerActivityInitialDisplay() {
        Espresso.onView(withId(R.id.button_start_timer)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_stop_timer)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_reset_timer)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.button_resume_timer)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        Espresso.onView(withId(R.id.number_picker_seconds)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.number_picker_minutes)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.number_picker_hours)).check(matches(isDisplayed()));
    }

    @Test
    public void testTimerStart() {
        // Set timer to 1 minute
        Espresso.onView(withId(R.id.number_picker_minutes)).perform(NumberPickerActions.setNumber(1));

        // Click start button
        Espresso.onView(withId(R.id.button_start_timer)).perform(ViewActions.click());

        // Verify the timer is started
        Espresso.onView(withId(R.id.button_stop_timer)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_reset_timer)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_start_timer)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testTimerStop() {
        // Set timer to 1 minute
        Espresso.onView(withId(R.id.number_picker_minutes)).perform(NumberPickerActions.setNumber(1));

        // Click start button
        Espresso.onView(withId(R.id.button_start_timer)).perform(ViewActions.click());

        // Click stop button
        Espresso.onView(withId(R.id.button_stop_timer)).perform(ViewActions.click());

        // Verify the timer is stopped
        Espresso.onView(withId(R.id.button_resume_timer)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_reset_timer)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.button_stop_timer)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }
}
