package com.example.todo_list.StopWatch;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.matcher.ViewMatchers;

import com.example.todo_list.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

@RunWith(AndroidJUnit4.class)
public class StopwatchFragmentTest {

    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(StopwatchFragment.class);
    }

    @Test
    public void testInitialState() {
        onView(withId(R.id.button_start_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_resume_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_reset_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testStartStopwatch() {
        onView(withId(R.id.button_start_stop_watch)).perform(click());
        onView(withId(R.id.button_stop_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_start_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testStopStopwatch() {
        onView(withId(R.id.button_start_stop_watch)).perform(click());
        onView(withId(R.id.button_stop_stop_watch)).perform(click());
        onView(withId(R.id.button_resume_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_reset_stop_watch)).check(matches(isDisplayed()));
    }

    @Test
    public void testResetStopwatch() {
        onView(withId(R.id.button_start_stop_watch)).perform(click());
        onView(withId(R.id.button_stop_stop_watch)).perform(click());
        onView(withId(R.id.button_reset_stop_watch)).perform(click());
        onView(withId(R.id.button_start_stop_watch)).check(matches(isDisplayed()));
        onView(withId(R.id.button_stop_stop_watch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void testResumeStopwatch() {
        onView(withId(R.id.button_start_stop_watch)).perform(click());
        onView(withId(R.id.button_stop_stop_watch)).perform(click());
        onView(withId(R.id.button_resume_stop_watch)).perform(click());
        onView(withId(R.id.button_stop_stop_watch)).check(matches(isDisplayed()));
    }
}
