package com.example.todo_list.AttendanceCalculator;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import com.example.todo_list.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AttendanceCalculatorTest {

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testCalculateNeededClasses() {
        try (ActivityScenario<AttendanceCalculator> scenario = ActivityScenario.launch(AttendanceCalculator.class)) {
            // Input values
            onView(withId(R.id.editTextTotalCredit)).perform(clearText(), typeText("3"));
            onView(withId(R.id.editTextTotalWeeks)).perform(clearText(), typeText("15"));
            onView(withId(R.id.editTextClassesAttended)).perform(clearText(), typeText("10"));
            onView(withId(R.id.editTextDesiredPercentage)).perform(clearText(), typeText("75"));
            onView(withId(R.id.editTextRemainingWeeks)).perform(clearText(), typeText("5"));

            // Close the keyboard
            closeSoftKeyboard();

            // Click the calculate button
            onView(withId(R.id.buttonCalculate)).perform(click());

            // Check the results
            onView(withId(R.id.textViewClassesLeft)).check(matches(withText("15"))); // Adjust expected value
            onView(withId(R.id.textViewClassesNeedToAttend)).check(matches(withText("23"))); // Adjust expected value
            onView(withId(R.id.textViewResult)).check(matches(withText("Sorry, you can't achieve 75.00% attendance in 5 weeks."))); // Adjust expected value
        }
    }

    @Test
    public void testResetFields() {
        try (ActivityScenario<AttendanceCalculator> scenario = ActivityScenario.launch(AttendanceCalculator.class)) {
            // Input values
            onView(withId(R.id.editTextTotalCredit)).perform(clearText(), typeText("3"));
            onView(withId(R.id.editTextTotalWeeks)).perform(clearText(), typeText("15"));
            onView(withId(R.id.editTextClassesAttended)).perform(clearText(), typeText("10"));
            onView(withId(R.id.editTextDesiredPercentage)).perform(clearText(), typeText("75"));
            onView(withId(R.id.editTextRemainingWeeks)).perform(clearText(), typeText("5"));

            // Click the reset button
            onView(withId(R.id.buttonReset)).perform(click());

            // Check that all fields are reset
            onView(withId(R.id.editTextTotalCredit)).check(matches(withText("")));
            onView(withId(R.id.editTextTotalWeeks)).check(matches(withText("")));
            onView(withId(R.id.editTextClassesAttended)).check(matches(withText("")));
            onView(withId(R.id.editTextDesiredPercentage)).check(matches(withText("")));
            onView(withId(R.id.editTextRemainingWeeks)).check(matches(withText("")));
            onView(withId(R.id.textViewClassesLeft)).check(matches(withText("")));
            onView(withId(R.id.textViewClassesNeedToAttend)).check(matches(withText("")));
            onView(withId(R.id.textViewResult)).check(matches(withText("")));
        }
    }

    @Test
    public void testNavigateToAttendancePercentage() {
        try (ActivityScenario<AttendanceCalculator> scenario = ActivityScenario.launch(AttendanceCalculator.class)) {
            // Click the navigate button
            onView(withId(R.id.buttonNavigate)).perform(click());

            // Check that the AttendancePercentage activity is started
            intended(hasComponent(AttendancePercentage.class.getName()));
        }
    }
}
